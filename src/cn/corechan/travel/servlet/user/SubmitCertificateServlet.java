package cn.corechan.travel.servlet.user;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.ResponseUtil;
import cn.corechan.travel.vo.Certificate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SubmitCertificateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        Status status = new Status();
        String phoneNumber = (String) req.getSession().getAttribute("phoneNumber");
        String ID = req.getParameter("ID");
        String realname = req.getParameter("realname");
        String contact = req.getParameter("contact");
        String address = req.getParameter("address");
        String IDPicUrl = req.getParameter("picURL");
        if (ID == null || realname == null || contact == null || address == null || IDPicUrl == null) {
            ResponseUtil.ResponseArgsMissing(resp);
            return;
        }
        Certificate certificate = new Certificate(phoneNumber, ID, realname, contact, address, IDPicUrl, 1, null);
        try {
            UserDAOProxy userDAOProxy = new UserDAOProxy();
            status = userDAOProxy.doCertificate(certificate);
            ResponseUtil.Render(resp, status);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
            status.setContent("SQLError", e.toString());
            ResponseUtil.Render(resp, status);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
