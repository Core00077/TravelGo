package cn.corechan.travel.servlet;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.Jackson;
import cn.corechan.travel.vo.User;
import com.fasterxml.jackson.databind.util.JSONPObject;

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
        response.setHeader("Access-Control-Allow-Origin", "*");
        request.setCharacterEncoding("UTF-8");
        String phoneNumber = request.getParameter("phoneNumber");
        String pwd = request.getParameter("password");
        UserDAOProxy loginProxy;
        Status loginStatus = null;
        try {
            loginProxy = new UserDAOProxy();
            loginStatus = loginProxy.doLogin(phoneNumber, pwd);
            if (loginStatus.getStatus().equals("sucess")) {
                HttpSession session = request.getSession();
                session.setAttribute("username", ((User)loginStatus.getData()).getName());
            }
        } catch (ClassNotFoundException | SQLException e) {
            loginStatus = new Status();
            loginStatus.setContent("loginFail","");
            loginStatus.setData(null);
        }
        String text = Jackson.toJson(loginStatus);
        response.getWriter().write(text);
    }
}
