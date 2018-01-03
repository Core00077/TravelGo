package cn.corechan.travel.servlet.good;

import cn.corechan.travel.dao.proxy.GoodDAOProxy;
import cn.corechan.travel.util.ResponseUtil;
import cn.corechan.travel.util.json.Status;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteGoodServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("UTF-8");
        String phoneNumber = (String) req.getSession().getAttribute("phoneNumber");
        if (phoneNumber == null) {
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        String goodId = req.getParameter("goodId");
        if (goodId == null) {
            ResponseUtil.ResponseArgsMissing(resp);
            return;
        }
        try {
            Status status=new GoodDAOProxy().findById(goodId);
            if(status.getStatus().equals("goodNotExist")){
                status.setData(null);
                ResponseUtil.Render(resp,status);
                return;
            }
            status=new GoodDAOProxy().deleteById(goodId);
            ResponseUtil.Render(resp,status);
        } catch (SQLException | ClassNotFoundException e) {
            ResponseUtil.ResponseError(resp,e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
