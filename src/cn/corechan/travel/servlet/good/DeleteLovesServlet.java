package cn.corechan.travel.servlet.good;

import cn.corechan.travel.dao.proxy.UserGoodDAOProxy;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteLovesServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phoneNumber = (String) req.getSession().getAttribute("phoneNumber");
        if (phoneNumber == null) {
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        String strRawGoodId=req.getParameter("goodId");
        String[] strGoodIds=strRawGoodId.split(",");
        ArrayList<String> goodIds=new ArrayList<>(Arrays.asList(strGoodIds));
        try {
            Status status=new UserGoodDAOProxy().DeleteLoves(phoneNumber,goodIds);
            ResponseUtil.Render(resp,status);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
            ResponseUtil.ResponseError(resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
