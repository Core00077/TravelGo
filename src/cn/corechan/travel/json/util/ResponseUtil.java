package cn.corechan.travel.json.util;

import cn.corechan.travel.json.Status;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {
    public static void Render(HttpServletResponse response, Status status)
            throws IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        String text = Jackson.toJson(status);
        response.getWriter().write(text);
    }

    public static void ResponseError(HttpServletResponse response)
            throws IOException{
        Status status = new Status();
        status.setContent("systemError","");
        status.setData(null);
        ResponseUtil.Render(response, status);
    }
}
