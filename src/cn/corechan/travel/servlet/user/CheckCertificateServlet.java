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

public class CheckCertificateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("UTF-8");
        String phoneNumber = (String) req.getSession().getAttribute("phoneNumber");
        if(phoneNumber==null)
            ResponseUtil.ResponseUnlogin(resp);
        Status status = new Status();
        try {
            UserDAOProxy userDAOProxy = new UserDAOProxy();
            status = userDAOProxy.findCertificate(phoneNumber);
            String msg = ((Certificate) status.getData()).getMsg();
            status.setData(msg);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        ResponseUtil.Render(resp, status);
    }
}
