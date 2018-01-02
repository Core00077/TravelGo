package cn.corechan.travel.servlet.user;

import cn.corechan.travel.json.Status;
import cn.corechan.travel.json.util.ResponseUtil;
import cn.corechan.travel.json.util.SessionUtil;
import cn.corechan.travel.vo.UserOnline;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String phoneNumber = (String) session.getAttribute("phoneNumber");
        UserOnline.getInstance().getOnlineMap().remove(phoneNumber);
        SessionUtil.SessionClean(request.getSession());
//        session.removeAttribute("phoneNumber");
        Status status = new Status();
        status.setContent("success", "Logout successfully!");
        ResponseUtil.Render(response, status);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
