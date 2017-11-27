package cn.corechan.travel.dao.proxy;

import cn.corechan.travel.dao.IGoodDAO;
import cn.corechan.travel.dao.impl.GoodDAOImpl;
import cn.corechan.travel.dbc.DatabaseConnection;
import cn.corechan.travel.factory.DatabaseConnectionFactor;
import cn.corechan.travel.json.Status;

import java.sql.SQLException;

public class GoodDAOProxy implements IGoodDAO {
    private DatabaseConnection dbc = null;
    private IGoodDAO goodDAO = null;

    public GoodDAOProxy() throws ClassNotFoundException, SQLException {
        dbc = DatabaseConnectionFactor.getMySQLDatabaseConnection();
        goodDAO = new GoodDAOImpl(dbc.getConnection());
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
        return null;
    }
}
