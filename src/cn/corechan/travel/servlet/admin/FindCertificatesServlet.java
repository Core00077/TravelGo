package cn.corechan.travel.servlet.admin;

import cn.corechan.travel.dao.proxy.AdminDAOProxy;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class FindCertificatesServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("UTF-8");
        resp.setContentType("UTF-8");
        String phoneNumber= (String) req.getSession().getAttribute("phoneNumber");
        if(phoneNumber==null){
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        try {
            AdminDAOProxy adminDAOProxy=new AdminDAOProxy();
            Status status=adminDAOProxy.findCertificates();
            ResponseUtil.Render(resp,status);
        } catch (SQLException | ClassNotFoundException e) {
            Status status=new Status();
            status.setContent("SQLError",e.toString());
            ResponseUtil.Render(resp,status);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
