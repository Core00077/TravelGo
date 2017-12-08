package cn.corechan.travel.dao;

import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.User;

import java.sql.SQLException;

public interface IUserDAO {
    Status doRegister(User user) throws SQLException;

    Status doChange(User newUser) throws SQLException;

    Status findByPhoneNumber(String phoneNumber) throws SQLException;

    Status doDelete(String phoneNumber) throws SQLException;
}
