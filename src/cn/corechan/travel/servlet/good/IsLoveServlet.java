package cn.corechan.travel.servlet.good;

import cn.corechan.travel.dao.proxy.UserGoodDAOProxy;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.Jackson;
import cn.corechan.travel.json.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class IsLoveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phoneNumber = (String) req.getSession().getAttribute("phoneNumber");
        String goodId = req.getParameter("goodId");
        Status status = null;
        try {
            status = new UserGoodDAOProxy().isLove(phoneNumber, goodId);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Jackson.toJson(status);
        ResponseUtil.Render(resp,status);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

}
