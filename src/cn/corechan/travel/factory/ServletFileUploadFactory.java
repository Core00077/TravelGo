package cn.corechan.travel.factory;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;

public class ServletFileUploadFactory {
    private ServletFileUploadFactory(){}
    public static ServletFileUpload getMyUpload(String tempPath){
        DiskFileItemFactory factory=new DiskFileItemFactory();
        factory.setSizeThreshold(1024*1024);
        File temp=new File(tempPath);
        if(temp.getParentFile().exists())
            factory.setRepository(temp);
        else
            if(temp.getParentFile().mkdir())
                factory.setRepository(temp);
        ServletFileUpload servletFileUpload=new ServletFileUpload(factory);
        servletFileUpload.setSizeMax(1024*1024*100);
        return servletFileUpload;
    }
}
