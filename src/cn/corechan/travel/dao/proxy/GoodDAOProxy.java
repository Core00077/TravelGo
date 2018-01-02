package cn.corechan.travel.dao.proxy;

import cn.corechan.travel.dao.IGoodDAO;
import cn.corechan.travel.dao.impl.GoodDAOImpl;
import cn.corechan.travel.dbc.DatabaseConnection;
import cn.corechan.travel.factory.DatabaseConnectionFactor;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.vo.Good;

import java.sql.SQLException;

public class GoodDAOProxy implements IGoodDAO {
    private DatabaseConnection dbc;
    private IGoodDAO goodDAO;

    public GoodDAOProxy() throws ClassNotFoundException, SQLException {
        dbc = DatabaseConnectionFactor.getMySQLDatabaseConnection();
        goodDAO = new GoodDAOImpl(dbc.getConnection());
    }

    @Override
    public Status findById(String Id) throws SQLException {
        Status status;
        try {
            status = goodDAO.findById(Id);
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
        } finally {
            dbc.close();
        }
        return status;
    }

    @Override
    public Status findBySeller(String phoneNumber) throws SQLException {
        try {
            return goodDAO.findBySeller(phoneNumber);
        } finally {
            dbc.close();
        }
    }

    public Status findLove(String phoneNumber) throws ClassNotFoundException, SQLException {
        Status loveStatus = new Status();
        loveStatus.setContent("failed", "");
        Status status;
        // 查找用户的收藏夹
        try {
            status = new UserGoodDAOProxy().findLoveIds(phoneNumber);
        } finally {
            dbc.close();
        }
        return status;
    }

    @Override
    public Status findAll() throws SQLException {
        Status status;
        try {
            status = goodDAO.findAll();
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
        } finally {
            dbc.close();
        }
        return status;
    }


}
