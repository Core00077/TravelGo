package cn.corechan.travel.json.util;

import cn.corechan.travel.vo.UserOnline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionUtil {
    public static boolean CheckUserOnline(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String phoneNumber = (String) session.getAttribute("phoneNumber");
        return UserOnline.getInstance().getOnlineMap().get(phoneNumber).equals(request.getSession().getId());
    }

    public static void SessionClean(HttpSession session) {
        session.setMaxInactiveInterval(0);
        session.invalidate();
    }
}
