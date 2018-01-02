package cn.corechan.travel.servlet.good.usergood;

import cn.corechan.travel.dao.proxy.GoodDAOProxy;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class FindLoveServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");              // 过滤器
        HttpSession session = request.getSession();
        String phoneNumber = (String)session.getAttribute("phoneNumber");
        if (phoneNumber == null) {
            ResponseUtil.ResponseUnlogin(response);
            return;
        }
        GoodDAOProxy loveIdsProxy;
        Status loveIdsStatus;
        try {
            loveIdsProxy = new GoodDAOProxy();
            loveIdsStatus = loveIdsProxy.findLove(phoneNumber);
            ResponseUtil.Render(response, loveIdsStatus);
        } catch (ClassNotFoundException | SQLException e) {
            ResponseUtil.ResponseError(response,e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
