package cn.corechan.travel.servlet.user;

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

public class FindSellerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");              // 过滤器
        HttpSession session = req.getSession();
        String phoneNumber = (String) session.getAttribute("phoneNumber");
        if (phoneNumber == null) {
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        String seller=req.getParameter("phoneNumber");
        try {
            Status status=new GoodDAOProxy().findBySeller(seller);
            ResponseUtil.Render(resp,status);
        }  catch (ClassNotFoundException | SQLException e) {
            ResponseUtil.ResponseError(resp,e);
        }
    }
}
