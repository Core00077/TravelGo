package cn.corechan.travel.json.util;

import cn.corechan.travel.json.Status;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class ResponseUtil {
    public static void Render(HttpServletResponse response, Status status) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        String text = Jackson.toJson(status);
        response.getWriter().write(text);
    }

    public static void ResponseError(HttpServletResponse response) throws IOException {
        Status status = new Status();
        status.setContent("systemError", "");
        status.setData(null);
        ResponseUtil.Render(response, status);
    }

    public static void ResponseUnlogin(HttpServletResponse response) throws IOException {
        Status status=new Status();
        status.setContent("unLogin","Please login first!");
        status.setData(null);
        Render(response,status);
    }
    public static void ResponseArgsMissing(HttpServletResponse response) throws IOException {
        Status status=new Status();
        status.setContent("argsMissing","Some arguments are missing!");
        status.setData(null);
        Render(response,status);
    }
}
