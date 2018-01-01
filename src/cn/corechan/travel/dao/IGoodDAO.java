package cn.corechan.travel.dao;

import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.Good;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IGoodDAO {

    Status findById(String Id) throws SQLException;

    Status findByCity(String city) throws SQLException;

    Status findBySeller(String phoneNumber) throws SQLException;

    Status findAll() throws SQLException;

    Status publishGood(Good good) throws SQLException;
}
