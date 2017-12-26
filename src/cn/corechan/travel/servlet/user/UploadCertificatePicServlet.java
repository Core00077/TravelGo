package cn.corechan.travel.servlet.user;

import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.ResponseUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class UploadCertificatePicServlet extends HttpServlet {
    private static String UPLOAD_HOME = "/";
    private static String UPLOAD_TEMP_HOME = "/";

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        UPLOAD_HOME = servletContext.getRealPath("/img/certificates");
        UPLOAD_TEMP_HOME = servletContext.getRealPath("/img/certificates/temp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("UTF-8");
        String phoneNubmer = (String) req.getSession().getAttribute("phoneNumber");
        DiskFileItemFactory factory = new DiskFileItemFactory();

        File temp = new File(UPLOAD_TEMP_HOME);
        if (!temp.exists())
            temp.getParentFile().mkdir();
        factory.setRepository(temp);
        factory.setSizeThreshold(1024 * 1024);

        ServletFileUpload upload = new ServletFileUpload(factory);

        Status status = new Status();
        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            String picUrl="/";
            for (FileItem fileItem : fileItems) {
                if (fileItem.isFormField()) {
                    continue;
                }
                String filename = fileItem.getName();
                String savename =phoneNubmer+"-"+new Date().getTime()+filename.substring(filename.lastIndexOf("."));
                picUrl="/img/certificates/"+savename;
                File file=new File(UPLOAD_HOME,savename);
                fileItem.write(file);
            }
            status.setData(picUrl);
            status.setContent("success","picture upload successfully!");
            ResponseUtil.Render(resp,status);
        } catch (Exception e) {
            status.setContent("Error", e.toString());
            status.setData(null);
            ResponseUtil.Render(resp,status);
        }

    }
}
