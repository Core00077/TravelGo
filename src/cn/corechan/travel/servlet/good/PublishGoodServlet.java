package cn.corechan.travel.servlet.good;

import cn.corechan.travel.dao.proxy.GoodDAOProxy;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.Jackson;
import cn.corechan.travel.json.util.ResponseUtil;
import cn.corechan.travel.vo.Good;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class PublishGoodServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("UTF-8");
        String phoneNumber= (String) req.getSession().getAttribute("phoneNumber");
        if(phoneNumber==null){
            ResponseUtil.ResponseUnlogin(resp);
            return;
        }
        String rawPicUrls = req.getParameter("picUrls");
        String rawName = req.getParameter("name");
        String rawPrice = req.getParameter("price");
        String rawCity = req.getParameter("city");
        String rawRoute = req.getParameter("route");
        String rawDescription = req.getParameter("description");
        if (rawPicUrls == null || rawName == null || rawPrice == null || rawCity == null || rawDescription == null) {
            ResponseUtil.ResponseArgsMissing(resp);
            return;
        }
        //将string数组转换为arrayList
        ArrayList<String> picUrls = new ArrayList<>(Arrays.asList(rawPicUrls.split(",")));
        String name = URLDecoder.decode(rawName, "utf-8");
        double price = Double.parseDouble(rawPrice);
        String city = URLDecoder.decode(rawCity, "utf-8");
        String route = URLDecoder.decode(rawRoute, "utf-8");
        String description = URLDecoder.decode(rawDescription, "utf-8");
        Status status = new Status();
        Good good = new Good();
        String id = String.valueOf(System.currentTimeMillis() / 10 % 100000000)+phoneNumber.substring(7);
        good.setId(id);
        good.setName(name);
        good.setPrice(price);
        good.setCity(city);
        good.setRoute(route);
        good.setDescription(description);
        good.setPictures(picUrls);
        good.setSeller(phoneNumber);
        try {
            status = new GoodDAOProxy().publishGood(good);
        } catch (SQLException | ClassNotFoundException e) {
            status.setMsg(e.toString());
            ResponseUtil.Render(resp, status);
            return;
        }
        ResponseUtil.Render(resp, status);
    }
}
