package cn.corechan.travel.servlet.order;

import cn.corechan.travel.json.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phoneNumber= (String) req.getSession().getAttribute("phoneNumber");
        if(phoneNumber==null){
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }

    }
}
