package cn.corechan.travel.servlet.user.certificate;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class CheckCertificateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("UTF-8");
        String phoneNumber = (String) req.getSession().getAttribute("phoneNumber");
        if(phoneNumber==null) {
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        Status status = new Status();
        try {
            UserDAOProxy userDAOProxy = new UserDAOProxy();
            status = userDAOProxy.checkCertificate(phoneNumber);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        ResponseUtil.Render(resp, status);
    }
}
