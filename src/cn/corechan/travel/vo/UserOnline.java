package cn.corechan.travel.vo;

import java.util.HashMap;

public class UserOnline {
    private HashMap<String, String> onlineMap = new HashMap<>();

    private UserOnline() {
    }

    public HashMap<String, String> getOnlineMap() {
        return onlineMap;
    }

    private static class UserOnlineFactory {
        private static UserOnline instance = new UserOnline();
    }

    public static UserOnline getInstance() {
        return UserOnlineFactory.instance;
    }
}
