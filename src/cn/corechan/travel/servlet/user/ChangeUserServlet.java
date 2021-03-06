package cn.corechan.travel.servlet.user;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.factory.ServletFileUploadFactory;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;
import cn.corechan.travel.util.SessionUtil;
import cn.corechan.travel.vo.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChangeUserServlet extends HttpServlet {
    private static String UPLOAD_TEMP_BASE = "/";
    private static String UPLOAD_BASE = "/";
    private ServletFileUpload upload;

    @Override
    public void init(ServletConfig config) {
        UPLOAD_BASE = config.getServletContext().getRealPath("/img/headpics");
        UPLOAD_TEMP_BASE = config.getServletContext().getRealPath("/img/headpics/temp");
        upload = ServletFileUploadFactory.getMyUpload(UPLOAD_TEMP_BASE);
        new File(UPLOAD_BASE).mkdir();
        new File(UPLOAD_TEMP_BASE).mkdir();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String phoneNumber = (String) request.getSession().getAttribute("phoneNumber");
        if (phoneNumber == null) {
            ResponseUtil.ResponseUnlogin(response);
            return;
        }
        if (!SessionUtil.CheckUserOnline(request)) {
            ResponseUtil.ResponseLoginByOther(response);
            SessionUtil.SessionClean(request.getSession());
            return;
        }
        Status status = new Status();
        HashMap<String, String> map = new HashMap<>();
        String headPic = null;
        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            for (FileItem fileitem : fileItems) {
                if (fileitem.isFormField()) {
                    map.put(fileitem.getFieldName(), fileitem.getString());
                } else {
                    if (fileitem.getFieldName().equals("headPicture")) {
                        String savename = new Date().getTime() + fileitem.getName().substring(fileitem.getName().lastIndexOf("."));
                        headPic = "/img/headpics/" +phoneNumber+"/"+ savename;
                        File file = new File(UPLOAD_BASE+"/"+phoneNumber+"/", savename);
                        if (file.getParentFile().exists())
                            fileitem.write(file);
                        else {
                            if (file.getParentFile().mkdir())
                                fileitem.write(file);
                            else {
                                ResponseUtil.Render(response, status);
                                return;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            ResponseUtil.ResponseError(response, e);
        }

        User newUser = new User();
        newUser.setPhoneNumber(phoneNumber);
        newUser.setName(map.get("username"));
        newUser.setRealName(map.get("realName"));
        newUser.setSex(map.get("sex"));
        newUser.setHometown(map.get("hometown"));
        newUser.setIntroduction(map.get("intro"));
        newUser.setHeadPicture(headPic);
        try {
            UserDAOProxy changeProxy = new UserDAOProxy();
            String headPicture=map.get("headPicture");
            if(headPicture!=null&&headPicture.equals("init")){
                newUser.setHeadPicture(((User)new UserDAOProxy().findByPhoneNumber(phoneNumber).getData()).getHeadPicture());
            }

            status = changeProxy.doChange(newUser);
            ResponseUtil.Render(response, status);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
            ResponseUtil.ResponseError(response, e);
        }
    }
}
