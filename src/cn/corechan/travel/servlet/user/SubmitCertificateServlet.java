package cn.corechan.travel.servlet.user;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.factory.ServletFileUploadFactory;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.ResponseUtil;
import cn.corechan.travel.vo.Certificate;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class SubmitCertificateServlet extends HttpServlet {
    private static String UPLOAD_TEMP_BASE = "/";
    private static String UPLOAD_BASE = "/";
    private ServletFileUpload upload;

    @Override
    public void init(ServletConfig config) throws ServletException {
        UPLOAD_BASE = config.getServletContext().getRealPath("/img/certificates");
        UPLOAD_TEMP_BASE = config.getServletContext().getRealPath("/img/certificate/temp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        upload = ServletFileUploadFactory.getMyUpload(UPLOAD_TEMP_BASE);
        upload.setSizeMax(1024 * 1024 * 4);
        Status status = new Status();
        String phoneNumber = (String) req.getSession().getAttribute("phoneNumber");
        if (phoneNumber == null) {
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        try {
            status = new UserDAOProxy().checkCertificate(phoneNumber);
            if (!status.getStatus().equals("IDNotExist") || !status.getStatus().equals("unpassed")) {
                ResponseUtil.Render(resp, status);
                return;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
            ResponseUtil.ResponseError(resp);
        }
        HashMap<String, String> map = new HashMap<>();
        String picUrl = "";
        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            for (FileItem fileItem : fileItems) {
                if (fileItem.isFormField()) {
                    map.put(fileItem.getFieldName(), fileItem.getString());
                }
            }
            if (map.get("ID") == null || map.get("realname") == null || map.get("contact") == null || map.get("address") == null) {
                ResponseUtil.ResponseArgsMissing(resp);
                return;
            }
            if (fileItems.size() > 5) {
                status.setMsg("one pic only!");
                ResponseUtil.Render(resp, status);
                return;
            }
            for (FileItem fileItem : fileItems) {
                String type = fileItem.getContentType();
                if (!fileItem.isFormField()) {//是文件
                    if (fileItem.getFieldName().equals("picURL")) {//是picURL
                        if (type.substring(0, type.lastIndexOf("/")).equals("image")) {//的确是图片
                            //重命名文件为账户id+后缀名
                            String savename = phoneNumber + System.currentTimeMillis() / 100000 + fileItem.getName().substring(fileItem.getName().lastIndexOf("."));
                            File file = new File(UPLOAD_BASE, savename);
                            if (file.getParentFile().exists())
                                fileItem.write(file);
                            else {
                                if (file.getParentFile().mkdir())
                                    fileItem.write(file);
                                else {
                                    status.setMsg("创建文件失败");
                                    ResponseUtil.Render(resp, status);
                                    return;
                                }
                            }
                            picUrl = "/img/certificates/" + savename;
                            break;//只需要一张图
                        }
                    }
                }
            }
        } catch (Exception e) {
            ResponseUtil.ResponseError(resp, e);
        }
        Certificate certificate = new Certificate(phoneNumber, map.get("ID"), map.get("realname"), map.get("contact"), map.get("address"), picUrl, 1, "");
        try {
            UserDAOProxy userDAOProxy = new UserDAOProxy();
            status = userDAOProxy.doCertificate(certificate);
            ResponseUtil.Render(resp, status);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
            status.setContent("SQLError", e.toString());
            ResponseUtil.Render(resp, status);
        }
    }
}
