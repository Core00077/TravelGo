package cn.corechan.travel.servlet.good;

import cn.corechan.travel.dao.proxy.GoodDAOProxy;
import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.factory.ServletFileUploadFactory;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.ResponseUtil;
import cn.corechan.travel.vo.Good;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PublishGoodServlet extends HttpServlet {
    private static String UPLOAD_BASE = "/";
    private static String UPLOAD_BASE_TEMP = "/";
    private ServletFileUpload upload;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        UPLOAD_BASE = servletContext.getRealPath("/img/goods");
        UPLOAD_BASE_TEMP = servletContext.getRealPath("/img/goods/temp");
        upload = ServletFileUploadFactory.getMyUpload(UPLOAD_BASE_TEMP);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("UTF-8");
        String phoneNumber = (String) req.getSession().getAttribute("phoneNumber");
        if (phoneNumber == null) {
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        Status status = new Status();
        try {
            status = new UserDAOProxy().findCertificate(phoneNumber);
            if (!status.getStatus().equals("passed")) {
                status.setData(null);
                ResponseUtil.Render(resp, status);
                return;
            }
        } catch (SQLException | ClassNotFoundException e) {
            status.setMsg(e.toString());
            ResponseUtil.Render(resp, status);
            return;
        }
        //form-data中的文字信息存入map
        HashMap<String, String> map = new HashMap<>();
        long timer = System.currentTimeMillis();
        String id = phoneNumber.substring(7) + String.valueOf(timer / 10 % 100000000);
        List<String> picUrls = new ArrayList<>();
        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            for (FileItem fileItem : fileItems) {
                if (fileItem.isFormField()) {
                    map.put(fileItem.getFieldName(), fileItem.getString());
                }
            }
            if (map.get("name") == null || map.get("price") == null || map.get("city") == null || map.get("route") == null || map.get("description") == null) {
                ResponseUtil.ResponseArgsMissing(resp);
                return;
            }
            //form-data中的图片信息写入服务器
            int i = 0;
            for (FileItem fileItem : fileItems) {
                if (!fileItem.isFormField() && fileItem.getFieldName().equals("pic")) {
                    i++;
                    String filename = fileItem.getName();
                    String savename = i + filename.substring(filename.lastIndexOf("."));
                    String picUrl = "/img/goods/" + id + "/" + savename;
                    File file = new File(UPLOAD_BASE + "\\" + id, savename);
                    if (!file.getParentFile().exists()) {
                        if (file.getParentFile().mkdir())
                            fileItem.write(file);
                    } else
                        fileItem.write(file);
                    picUrls.add(picUrl);
                }
            }
            if (i == 0) {
                ResponseUtil.ResponseArgsMissing(resp);
                return;
            }
        } catch (Exception e) {
            ResponseUtil.ResponseError(resp, e);
        }
//老代码，使用parameter+两个接口方案
/*        String rawPicUrls = req.getParameter("picUrls");
        String rawName = req.getParameter("name");
        String rawPrice = req.getParameter("price");
        String rawCity = req.getParameter("city");
        String rawRoute = req.getParameter("route");
        String rawDescription = req.getParameter("description");
        if (rawPicUrls == null || rawName == null || rawPrice == null || rawCity == null || rawDescription == null) {
            ResponseUtil.ResponseArgsMissing(resp);
            return;
        }
        //将string数组转换为arrayList
        ArrayList<String> picUrls = new ArrayList<>(Arrays.asList(rawPicUrls.split(",")));

        String name = URLDecoder.decode(rawName, "utf-8");
        double price = Double.parseDouble(rawPrice);
        String city = URLDecoder.decode(rawCity, "utf-8");
        String route = URLDecoder.decode(rawRoute, "utf-8");
        String description = URLDecoder.decode(rawDescription, "utf-8");
*/
        Good good = new Good();
        good.setId(id);
        good.setName(URLDecoder.decode(map.get("name"), "utf-8"));
        good.setPrice(Double.parseDouble(map.get("price")));
        good.setCity(URLDecoder.decode(map.get("city"), "UTF-8"));
        good.setRoute(URLDecoder.decode(map.get("route"), "UTF-8"));
        good.setDescription(URLDecoder.decode(map.get("description"), "UTF-8"));
        good.setPictures(picUrls);
        good.setSeller(phoneNumber);
        good.setPubtime(String.valueOf(timer));
        try {
            status = new GoodDAOProxy().publishGood(good);
        } catch (SQLException | ClassNotFoundException e) {
            status.setMsg(e.toString());
            ResponseUtil.Render(resp, status);
            return;
        }
        ResponseUtil.Render(resp, status);
    }
}
