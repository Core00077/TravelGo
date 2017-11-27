package cn.corechan.travel.servlet;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.Jackson;
import cn.corechan.travel.vo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 判断session是否为空
//        HttpSession session = request.getSession();
        response.setHeader("Access-Control-Allow-Origin", "*");
        String phoneNumber = request.getParameter("phoneNumber");
        String pwd = request.getParameter("password");
        UserDAOProxy loginProxy;
        User user = null;
        try {
            loginProxy = new UserDAOProxy();
            user = loginProxy.doLogin(phoneNumber, pwd);
            System.out.println(phoneNumber);
            System.out.println(pwd);
            if (user == null) {

            } else {
//                Status status = new Status();
//                status.setStatus("ok");
//                status.setMsg("");
//                status.setData(null);
//
//                String text = Jackson.toJson(status);
//
//                System.out.println(text);
//                response.getWriter().write(text);
            }
        } catch (ClassNotFoundException | SQLException e) {

        }
    }
}
