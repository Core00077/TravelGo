package cn.corechan.travel.servlet.good.usergood;

import cn.corechan.travel.dao.proxy.UserGoodDAOProxy;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteLoveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phoneNumber = (String) req.getSession().getAttribute("phoneNumber");
        if (phoneNumber == null) {
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        String goodId = req.getParameter("goodId");
        Status status = new Status();
        try {
            status = new UserGoodDAOProxy().DeleteLove(phoneNumber, goodId);
            ResponseUtil.Render(resp, status);
        } catch (SQLException e) {
            System.out.println(e.toString());
            status.setContent("SQLError",e.toString());
            ResponseUtil.Render(resp, status);
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
            ResponseUtil.ResponseError(resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
