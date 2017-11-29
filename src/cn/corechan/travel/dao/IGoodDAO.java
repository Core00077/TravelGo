package cn.corechan.travel.dao;

import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.Good;

import java.sql.SQLException;

public interface IGoodDAO {
    Status addLove(String Id) throws SQLException;

    Status findById(String Id) throws SQLException;

    Status findByCity(String city) throws SQLException;

    Status findAll() throws SQLException;

}
