package cn.corechan.travel.servlet.good;

import cn.corechan.travel.dao.proxy.UserGoodDAOProxy;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class FindLoveIdsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        UserGoodDAOProxy loveIdsProxy;
        Status loveIdsStatus;
        HttpSession session = request.getSession();
        if (!session.isNew()) {
            String phoneNumber = (String) session.getAttribute("phoneNumber");
            if (phoneNumber != null) {
                try {
                    loveIdsProxy = new UserGoodDAOProxy();
                    loveIdsStatus = loveIdsProxy.findLoveIds(phoneNumber);
                    ResponseUtil.Render(response, loveIdsStatus);
                } catch (ClassNotFoundException | SQLException e) {
                    ResponseUtil.ResponseError(response);
                }
            } else {
                loveIdsStatus = new Status();
                loveIdsStatus.setStatus("unlogin");
                ResponseUtil.Render(response, loveIdsStatus);
            }
        } else {
            loveIdsStatus = new Status();
            loveIdsStatus.setStatus("unlogin");
            ResponseUtil.Render(response, loveIdsStatus);
        }
    }
}