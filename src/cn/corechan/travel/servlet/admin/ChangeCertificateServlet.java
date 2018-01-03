package cn.corechan.travel.servlet.admin;

import cn.corechan.travel.dao.proxy.AdminDAOProxy;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ChangeCertificateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("UTF-8");
        String admin = (String) req.getSession().getAttribute("adminPhoneNumber");
        if (admin == null) {
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        String phoneNumber = req.getParameter("phoneNumber");
        String str_s = req.getParameter("status");
        if (phoneNumber == null || str_s == null) {
            ResponseUtil.ResponseArgsMissing(resp);
            return;
        }
        String msg = req.getParameter("msg");
        int s = 1;
        switch (str_s) {
            case "passed":
                s = 2;
                break;
            case "unpassed":
                s = 0;
                break;
        }
        try {
            AdminDAOProxy adminDAOProxy = new AdminDAOProxy();
            Status status = adminDAOProxy.setCertificateStatus(phoneNumber, s,msg);
            ResponseUtil.Render(resp, status);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
            ResponseUtil.ResponseError(resp, e);
        }
    }
}
