package cn.corechan.travel.servlet.user;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.ResponseUtil;
import cn.corechan.travel.json.util.SessionUtil;
import cn.corechan.travel.vo.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class FindUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");              // 过滤器
        HttpSession session = request.getSession();
        String phoneNumber = (String) session.getAttribute("phoneNumber");
        if (phoneNumber == null) {
            ResponseUtil.ResponseUnlogin(response);
            return;
        }
        if (!SessionUtil.CheckUserOnline(request)) {
            ResponseUtil.ResponseLoginByOther(response);
            SessionUtil.SessionClean(session);
            return;
        }
        UserDAOProxy findUserProxy;
        Status findStatus;
        try {
            findUserProxy = new UserDAOProxy();
            findStatus = findUserProxy.findByPhoneNumber(phoneNumber);
            User user = ((User) findStatus.getData());
            user.setPwd("");
            findStatus.setData(user);
            ResponseUtil.Render(response, findStatus);
        } catch (ClassNotFoundException | SQLException e) {
            ResponseUtil.ResponseError(response, e);
        }
    }
}
