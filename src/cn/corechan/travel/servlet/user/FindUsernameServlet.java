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
import java.util.HashMap;
import java.util.Map;

public class FindUsernameServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");              // 过滤器
        HttpSession session = request.getSession();
        String phoneNumber = (String) session.getAttribute("phoneNumber");
        phoneNumber = "15871731525";             // findUser测试专用
        UserDAOProxy findUserProxy;
        Status findStatus;
        try {
            findUserProxy = new UserDAOProxy();
            findStatus = findUserProxy.findByPhoneNumber(phoneNumber);
            Map<String, String> username = new HashMap<>();
            username.put("username", ((User)findStatus.getData()).getName());
            findStatus.setData(username);
            ResponseUtil.Render(response, findStatus);
        } catch (ClassNotFoundException | SQLException e) {
            ResponseUtil.ResponseError(response);
        }
    }
}
