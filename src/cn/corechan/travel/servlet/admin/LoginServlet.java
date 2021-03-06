package cn.corechan.travel.servlet.admin;

import cn.corechan.travel.dao.proxy.AdminDAOProxy;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("UTF-8");

        String phoneNumber = req.getParameter("phoneNumber");
        String pwd = req.getParameter("password");
        try {
            AdminDAOProxy adminDAOProxy=new AdminDAOProxy();
            Status status=adminDAOProxy.loginAdmin(phoneNumber,pwd);
            ResponseUtil.Render(resp,status);
            if(status.getStatus().equals("success")){
                req.getSession().setAttribute("adminPhoneNumber",phoneNumber);
                req.getSession().setMaxInactiveInterval(0);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
            Status status=new Status();
            status.setContent("SQLError",e.toString());
            ResponseUtil.Render(resp,status);
        }
    }
}
