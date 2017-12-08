package cn.corechan.travel.dao.proxy;

import cn.corechan.travel.dao.IUserGoodDAO;
import cn.corechan.travel.dao.impl.UserGoodDAOImpl;
import cn.corechan.travel.dbc.DatabaseConnection;
import cn.corechan.travel.factory.DatabaseConnectionFactor;
import cn.corechan.travel.json.Status;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserGoodDAOProxy implements IUserGoodDAO {
    private DatabaseConnection dbc = null;
    private IUserGoodDAO userGoodDAO = null;

    public UserGoodDAOProxy() throws ClassNotFoundException, SQLException {
        dbc = DatabaseConnectionFactor.getMySQLDatabaseConnection();
        userGoodDAO = new UserGoodDAOImpl(dbc.getConnection());
    }

    @Override
    public Status findLoveIds(String phoneNumber) throws SQLException {
        Status status;
        try {
            status = userGoodDAO.findLoveIds(phoneNumber);
        } finally {
            dbc.close();
        }
        return status;
    }

    @Override
    public Status AddLove(String phoneNumber, String goodId) throws SQLException {
        Status status;
        try {
            status = userGoodDAO.AddLove(phoneNumber, goodId);
        } finally {
            dbc.close();
        }
        return status;
    }

    @Override
    public Status isLove(String phoneNumber, String goodId) throws SQLException {
        Status status;
        try {
            status = userGoodDAO.isLove(phoneNumber, goodId);
        }finally {
            dbc.close();
        }
        return status;
    }

    @Override
    public Status DeleteLove(String phoneNumber, String goodId) throws SQLException {
        Status status;
        try {
            status=userGoodDAO.DeleteLove(phoneNumber, goodId);
        }finally {
            dbc.close();
        }
        return status;
    }

    @Override
    public Status DeleteLoves(String phoneNumber, ArrayList<String> goodIds) throws SQLException {
        Status status;
        try {
            status=userGoodDAO.DeleteLoves(phoneNumber, goodIds);
        }finally {
            dbc.close();
        }
        return status;
    }
}
