package cn.corechan.travel.servlet;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.vo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        User user = new User();
        user.setPhoneNumber(request.getParameter("phoneNumber"));
        user.setName(request.getParameter("username"));
        user.setPwd(request.getParameter("password"));
        UserDAOProxy registerProxy;
        try {
            registerProxy = new UserDAOProxy();
            if (registerProxy.doRegister(user) == null) {
//                request.getRequestDispatcher("/html/main.html").forward(request, response);
                response.getWriter().write("<hr>chenggong<hr>");
            } else {
//                request.getRequestDispatcher("/html/login&register.html").forward(request, response);
                response.getWriter().write("<hr>shibai<hr>");
            }
        } catch (ClassNotFoundException | SQLException e) {
//            request.getRequestDispatcher("/html/login&register.html").forward(request, response);
            response.getWriter().write("<hr>shibai<hr>");
        }
    }
}
