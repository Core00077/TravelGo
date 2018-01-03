package cn.corechan.travel.servlet.user;

import cn.corechan.travel.dao.proxy.UserDAOProxy;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.util.ResponseUtil;
import cn.corechan.travel.vo.UserOnline;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");              // 过滤器
        String phoneNumber = request.getParameter("phoneNumber");
        String pwd = request.getParameter("password");
        String remember = request.getParameter("remember");
        if (remember == null)
            remember = "0";
        UserDAOProxy loginProxy;
        Status loginStatus;
        try {
            loginProxy = new UserDAOProxy();
            loginStatus = loginProxy.doLogin(phoneNumber, pwd);
            if (loginStatus.getStatus().equals("success")) {
                HttpSession session = request.getSession();
                session.setAttribute("phoneNumber", phoneNumber);
                if (remember.equals("1")) {
                    session.setMaxInactiveInterval(7 * 24 * 3600);
                }
            }
            UserOnline.getInstance().getOnlineMap().put(phoneNumber, request.getSession().getId());
            ResponseUtil.Render(response, loginStatus);
        } catch (ClassNotFoundException | SQLException e) {
            ResponseUtil.ResponseError(response);
        }
    }
}
