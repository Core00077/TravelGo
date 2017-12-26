package cn.corechan.travel.servlet.user;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.ResponseUtil;
import cn.corechan.travel.vo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ChangeUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String phoneNumber = (String) request.getSession().getAttribute("phoneNumber");
        if (phoneNumber == null) {
            ResponseUtil.ResponseUnlogin(response);
            return;
        }
        UserDAOProxy changeProxy;
        Status changeStatus;
        User newUser = new User();
        newUser.setPhoneNumber(phoneNumber);
        newUser.setName(request.getParameter("username"));
        newUser.setRealName(request.getParameter("realName"));
        newUser.setSex(request.getParameter("sex"));
        newUser.setHometown(request.getParameter("hometown"));
        newUser.setIntroduction(request.getParameter("intro"));
        try {
            changeProxy = new UserDAOProxy();
            changeStatus = changeProxy.doChange(newUser);
            ResponseUtil.Render(response, changeStatus);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("e.toString() = " + e.toString());
            ResponseUtil.ResponseError(response);
        }
    }
}
