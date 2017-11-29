package cn.corechan.travel.dao;

import cn.corechan.travel.json.Status;

import java.sql.SQLException;

public interface IUserGoodDAO {
    Status findLoveIds(String phoneNumber) throws SQLException;
}
