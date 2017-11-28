package cn.corechan.travel.servlet.user;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.ResponseUtil;
import cn.corechan.travel.vo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        UserDAOProxy registerProxy;
        Status registerStatus;
        User user = new User();
        user.setPhoneNumber(request.getParameter("phoneNumber"));
        user.setName(request.getParameter("username"));
        user.setPwd(request.getParameter("password"));
        try {
            registerProxy = new UserDAOProxy();
            registerStatus = registerProxy.doRegister(user);
            if (registerStatus.getStatus().equals("success")) {
                HttpSession session = request.getSession();
                session.setAttribute("phoneNumber", user.getPhoneNumber());
            }
            ResponseUtil.Render(response, registerStatus);
        } catch (ClassNotFoundException | SQLException e) {
            ResponseUtil.ResponseError(response);
        }
    }


}
