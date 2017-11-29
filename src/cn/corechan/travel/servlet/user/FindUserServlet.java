package cn.corechan.travel.servlet.user;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class FindUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");              // 过滤器
        HttpSession session = request.getSession();
        String phoneNumber = (String) session.getAttribute("phoneNumber");
        UserDAOProxy findUserProxy;
        Status findStatus;
        try {
            findUserProxy = new UserDAOProxy();
            findStatus = findUserProxy.findByPhoneNumber(phoneNumber);

            ResponseUtil.Render(response, findStatus);
        } catch (ClassNotFoundException | SQLException e) {
            ResponseUtil.ResponseError(response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
