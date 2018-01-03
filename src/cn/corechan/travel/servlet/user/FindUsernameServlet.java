package cn.corechan.travel.servlet.user;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;
import cn.corechan.travel.util.SessionUtil;
import cn.corechan.travel.vo.User;

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
            throws IOException {
        request.setCharacterEncoding("UTF-8");              // 过滤器
        HttpSession session = request.getSession();
        UserDAOProxy findUserProxy;
        Status findStatus;
        if (!session.isNew()) {
            String phoneNumber = (String) session.getAttribute("phoneNumber");
            if (phoneNumber != null) {
                if (!SessionUtil.CheckUserOnline(request)) {
                    ResponseUtil.ResponseLoginByOther(response);
                    SessionUtil.SessionClean(session);
                    return;
                }
                try {
                    findUserProxy = new UserDAOProxy();
                    findStatus = findUserProxy.findByPhoneNumber(phoneNumber);
                    Map<String, String> username = new HashMap<>();
                    username.put("username", ((User) findStatus.getData()).getName());
                    findStatus.setStatus("logined");
                    findStatus.setData(username);
                    ResponseUtil.Render(response, findStatus);
                } catch (ClassNotFoundException | SQLException e) {
                    ResponseUtil.ResponseError(response);
                }
            } else {
                findStatus = new Status();
                findStatus.setStatus("unlogin");
                ResponseUtil.Render(response, findStatus);
            }
        } else {
            findStatus = new Status();
            findStatus.setStatus("unlogin");
            ResponseUtil.Render(response, findStatus);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }
}
