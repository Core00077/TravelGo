package cn.corechan.travel.dao;

import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.vo.Good;

import java.sql.SQLException;

public interface IGoodDAO {

    Status findById(String Id) throws SQLException;

    Status findByCity(String city) throws SQLException;

    Status findBySeller(String phoneNumber) throws SQLException;

    Status findAll() throws SQLException;

    Status publishGood(Good good) throws SQLException;

    Status deleteById(String goodId) throws SQLException;
}
