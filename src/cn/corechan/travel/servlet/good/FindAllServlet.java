package cn.corechan.travel.servlet.good;

import cn.corechan.travel.dao.proxy.GoodDAOProxy;
import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class FindAllServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");              // 过滤器
        GoodDAOProxy findAllProxy;
        Status findAllStatus;
        try {
            findAllProxy = new GoodDAOProxy();
            findAllStatus = findAllProxy.findAll();
            ResponseUtil.Render(response, findAllStatus);
        } catch (ClassNotFoundException | SQLException e) {
            ResponseUtil.ResponseError(response);
        }
    }
}
