package cn.corechan.travel.dao;

import cn.corechan.travel.json.Status;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserGoodDAO {
    Status findLoveIds(String phoneNumber) throws SQLException;

    Status AddLove(String phoneNumber, String goodId) throws SQLException;

    Status isLove(String phoneNumber, String goodId) throws SQLException;

    Status DeleteLove(String phoneNumber, String goodId) throws SQLException;

    Status DeleteLoves(String phoneNumber, ArrayList<String> goodIds) throws SQLException;
}
