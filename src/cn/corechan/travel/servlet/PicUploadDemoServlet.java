package cn.corechan.travel.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;


public class PicUploadDemoServlet extends HttpServlet {
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
        resp.setContentType("utf-8");
        resp.setContentType("text/html;charset=UTF-8");

        DiskFileItemFactory factory = new DiskFileItemFactory();

        factory.setRepository(new File(UPLOAD_TEMP_HOME));
        factory.setSizeThreshold(1024 * 1024);

        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            List<FileItem> list = upload.parseRequest(req);
            PrintWriter writer=null;
            for (FileItem fileItem : list) {
                //获取文件名字并去掉斜杠
                if (fileItem.isFormField()) {
                    continue;
                }
                String filename = fileItem.getName().substring(fileItem.getName().lastIndexOf("/") + 1);
                String saveName = new Date().getTime() + filename.substring(filename.lastIndexOf("."));
                String picUrl = "http://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/img/goods/checking/" + saveName;
                System.out.println("存放目录：" + UPLOAD_HOME);
                System.out.println("文件名：" + filename);
                System.out.println("浏览器访问路径：" + picUrl);
                File file = new File(UPLOAD_HOME, saveName);
                file.getParentFile().mkdir();
                fileItem.write(file);
                writer = resp.getWriter();
                writer.print("{");
                writer.print("\"msg\":\"文件大小:" + fileItem.getSize() + ",文件名:" + filename + "\"");
                writer.print(",\"picUrl\":\"" + picUrl + "\"");
                writer.print("}");

            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
