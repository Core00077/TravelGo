package cn.corechan.travel.servlet.user.certificate;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ViewCertificateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String phoneNumber= (String) req.getSession().getAttribute("phoneNumber");
        if(phoneNumber==null) {
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        UserDAOProxy userDAOProxy;
        Status status=new Status();
        try {
            userDAOProxy = new UserDAOProxy();
            status=userDAOProxy.findCertificate(phoneNumber);
            ResponseUtil.Render(resp,status);
        } catch (ClassNotFoundException | SQLException e) {
            status.setContent("SQLError",e.toString());
            System.out.println(e.toString());
            ResponseUtil.Render(resp,status);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
