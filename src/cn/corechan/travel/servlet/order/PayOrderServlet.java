package cn.corechan.travel.servlet.order;

import cn.corechan.travel.dao.proxy.OrderDAOProxy;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class PayOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("UTF-8");
        String phoneNumber = (String) req.getSession().getAttribute("phoneNumber");
        if (phoneNumber == null) {
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        String orderId = req.getParameter("orderId");
        try {
            Status status=new OrderDAOProxy().PayOrder(orderId);
            ResponseUtil.Render(resp, status);
        } catch (SQLException | ClassNotFoundException e) {
            ResponseUtil.ResponseError(resp, e);
        }
    }
}
