package cn.corechan.travel.servlet.good;

import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.Jackson;
import cn.corechan.travel.json.util.ResponseUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UploadPicServlet extends HttpServlet {
    private static String UPLOAD_HOME = "/";
    private static String UPLOAD_TEMP_HOME = "/";

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        UPLOAD_HOME = servletContext.getRealPath("/img/goods/checking");
        UPLOAD_TEMP_HOME = servletContext.getRealPath("/img/goods/checking/temp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("utf-8");

        DiskFileItemFactory factory = new DiskFileItemFactory();

        File temp = new File(UPLOAD_TEMP_HOME);
        if(!temp.exists())
            temp.getParentFile().mkdir();
        factory.setRepository(temp);
        factory.setSizeThreshold(1024 * 1024);

        ServletFileUpload upload = new ServletFileUpload(factory);

        Status status = new Status();
        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            ArrayList<String> picUrls = new ArrayList<>();
            for (FileItem fileItem : fileItems) {
                if (fileItem.isFormField())
                    continue;
                String filename = fileItem.getName();
                String savename = new Date().getTime() + filename.substring(filename.lastIndexOf("."));
                String picUrl = "/img/goods/checking/" + savename;
                File file = new File(UPLOAD_HOME, savename);
                if(!temp.exists())
                    file.getParentFile().mkdir();
                fileItem.write(file);
                picUrls.add(picUrl);
            }
            status.setData(picUrls);
            status.setContent("success", "All pics have been uploaded successfully!");
        } catch (Exception e) {
            status.setContent("Error", e.toString());
            status.setData(null);
        }
        Jackson.toJson(status);
        ResponseUtil.Render(resp, status);
    }
}
