package cn.corechan.travel.servlet.user;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;
import cn.corechan.travel.vo.Contact;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AddContactServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String phoneNumber = (String) req.getSession().getAttribute("phoneNumber");
        if (phoneNumber == null) {
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        Contact contact = new Contact(req.getParameter("name"), req.getParameter("phoneNumber"));
        try {
            Status status = new UserDAOProxy().addContact(phoneNumber, contact);
            ResponseUtil.Render(resp, status);
        } catch (SQLException | ClassNotFoundException e) {
            ResponseUtil.ResponseError(resp, e);
        }
    }
}
