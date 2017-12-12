package cn.corechan.travel.dao.proxy;

import cn.corechan.travel.dao.IGoodDAO;
import cn.corechan.travel.dao.impl.GoodDAOImpl;
import cn.corechan.travel.dbc.DatabaseConnection;
import cn.corechan.travel.factory.DatabaseConnectionFactor;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.Good;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodDAOProxy implements IGoodDAO {
    private DatabaseConnection dbc = null;
    private IGoodDAO goodDAO = null;

    public GoodDAOProxy() throws ClassNotFoundException, SQLException {
        dbc = DatabaseConnectionFactor.getMySQLDatabaseConnection();
        goodDAO = new GoodDAOImpl(dbc.getConnection());
    }

    @Override
    public Status findById(int Id) throws SQLException {
        Status status;
        try {
            status = goodDAO.findById(Id);
        } catch (SQLException e) {
            throw e;
        } finally {
            dbc.close();
        }
        return status;
    }

    @Override
    public Status findByCity(String city) throws SQLException {
        Status status;
        try {
            status = goodDAO.findByCity(city);
        } catch (SQLException e) {
            throw e;
        } finally {
            dbc.close();
        }
        return status;
    }

    public Status findLove(String phoneNumber) throws ClassNotFoundException,SQLException {
        Status loveStatus = new Status();
        loveStatus.setContent("failed","");
        Status status;
        // 查找用户的收藏夹
        try {
            status = new UserGoodDAOProxy().findLoveIds(phoneNumber);
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        }
        return status;
    }

    @Override
    public Status findAll() throws SQLException {
        Status status;
        try {
            status = goodDAO.findAll();
        } catch (SQLException e) {
            throw e;
        } finally {
            dbc.close();
        }
        return status;
    }

    @Override
    public Status publishGood(Good good) throws SQLException {
        Status status;
        try {
            status = goodDAO.publishGood(good);
        }finally {
            dbc.close();
        }
        return status;
    }


}
