package cn.corechan.travel.servlet.user;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class findContactsServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("UTF-8");
        String phoneNumber= (String) req.getSession().getAttribute("phoneNumber");
        if(phoneNumber==null){
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        try {
            UserDAOProxy userDAOProxy=new UserDAOProxy();
            Status status=userDAOProxy.findContacts(phoneNumber);
            ResponseUtil.Render(resp,status);
        } catch (ClassNotFoundException | SQLException e) {
            ResponseUtil.ResponseError(resp,e);
        }
    }
}
