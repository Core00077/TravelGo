package cn.corechan.travel.servlet.good.usergood;

import cn.corechan.travel.dao.proxy.UserGoodDAOProxy;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class AddLoveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setCharacterEncoding("UTF-8");
        String phoneNumber = (String) session.getAttribute("phoneNumber");
        if (phoneNumber == null) {
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        String goodId = req.getParameter("goodId");
        try {
            Status status = new UserGoodDAOProxy().AddLove(phoneNumber, goodId);
            ResponseUtil.Render(resp, status);
        } catch (SQLException | ClassNotFoundException e) {
            ResponseUtil.ResponseError(resp);
            System.out.println(e.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

}
