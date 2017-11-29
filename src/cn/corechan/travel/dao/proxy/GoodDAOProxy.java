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
    public Status addLove(String Id) throws SQLException {
        return null;
    }

    @Override
    public Status findById(String Id) throws SQLException {
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
        Status findUserGoodStatus;
        // 查找用户的收藏夹
        try {
            findUserGoodStatus = new UserGoodDAOProxy().findLoveIds(phoneNumber);
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        }

        // 根据收藏夹内的goodId查找商品
        List<String> goodIds = (List<String>) findUserGoodStatus.getData();
        List<Good> loves = new ArrayList<>();
        for (int i = 0; i < goodIds.size(); i++) {
            try {
                Status status = goodDAO.findById(goodIds.get(i));
                loves.add((Good)status.getData());
            } catch (SQLException e) {

            }
        }
        loveStatus.setData(loves);
        return loveStatus;
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
}
