package cn.corechan.travel.servlet.order;

import cn.corechan.travel.dao.proxy.GoodDAOProxy;
import cn.corechan.travel.dao.proxy.OrderDAOProxy;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;
import cn.corechan.travel.vo.Contact;
import cn.corechan.travel.vo.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phoneNumber = (String) req.getSession().getAttribute("phoneNumber");
        if (phoneNumber == null) {
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        String goodId = req.getParameter("goodId");
        //确认商品存在
        try {
            Status status = new GoodDAOProxy().findById(goodId);
            if (status.getStatus().equals("goodNotExist")) {
                ResponseUtil.Render(resp, status);
                return;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        String gotime = req.getParameter("gotime");
        if(gotime.length()==10)
            gotime+="000";
        int people = Integer.parseInt(req.getParameter("people"));
        String note = req.getParameter("note");
        String[] rawContacts = req.getParameter("contacts").split(",");
        List<Contact> contacts = new ArrayList<>();
        for (String i : rawContacts) {
            String[] rawContact = i.split("\\@");
            if(rawContact.length<2){
                ResponseUtil.ResponseArgsMissing(resp);
            }
            contacts.add(new Contact(rawContact[0], rawContact[1]));
        }
        Order order = new Order(goodId, phoneNumber, gotime, people, note, contacts);
        try {
            OrderDAOProxy orderDAOProxy = new OrderDAOProxy();
            Status status = orderDAOProxy.CreateOrder(order);
            ResponseUtil.Render(resp, status);
        } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
            ResponseUtil.ResponseError(resp, e);
        }

    }
}
