package cn.corechan.travel.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;

@WebServlet(urlPatterns = "/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin","*");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("UTF-8");
        System.out.println(req.getContentType());
        Enumeration<String> parameterNames = req.getParameterNames();
        resp.getWriter().println("------这是parameter部分------");
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            resp.getWriter().println(name + "\t" + req.getParameter(name));
        }
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024);
        factory.setRepository(new File("testcore"));
        ServletFileUpload upload = new ServletFileUpload(factory);
        if (isMultipart) {
            try {
                List<FileItem> items = upload.parseRequest(req);
                resp.getWriter().println("------这是form-data部分------");
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        String value = item.getString();
                        resp.getWriter().println(name + "\t" + value);
                    } else {
                        String name = item.getName();
                        String field = item.getFieldName();
                        long size = item.getSize();
                        String type = item.getContentType();
                        String url = "/testcore/" + name;
                        resp.getWriter().println(field + "\t" + type + "\t" + size + "b\t" + url);
                        File file = new File(getServletConfig().getServletContext().getRealPath("testcore"), name);
                        if (!file.getParentFile().exists()) {
                            if (file.getParentFile().mkdir())
                                item.write(file);
                        } else
                            item.write(file);

                        System.out.println(file.getAbsolutePath());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(req.getContentType().equals("application/json")){
            InputStream inputStream=req.getInputStream();
            byte[] buffer=new byte[1024];
            StringBuilder sb=new StringBuilder();
            int len;
            while ((len=inputStream.read(buffer))>0){
                sb.append(new String(buffer).substring(0,len));
            }
            System.out.println(sb);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
