package cn.corechan.travel.servlet.good;

import cn.corechan.travel.dao.proxy.GoodDAOProxy;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

public class FindGoodByCityServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");              // 过滤器
        GoodDAOProxy findProxy;
        Status findStatus;
        String city = URLDecoder.decode(request.getParameter("city"), "UTF-8");
        try {
            findProxy = new GoodDAOProxy();
            findStatus = findProxy.findByCity(city);
            ResponseUtil.Render(response, findStatus);
        } catch (ClassNotFoundException | SQLException e) {
            ResponseUtil.ResponseError(response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}