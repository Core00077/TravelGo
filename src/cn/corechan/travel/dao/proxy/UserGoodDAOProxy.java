package cn.corechan.travel.dao.proxy;

import cn.corechan.travel.dao.IUserGoodDAO;
import cn.corechan.travel.dao.impl.UserGoodDAOImpl;
import cn.corechan.travel.dbc.DatabaseConnection;
import cn.corechan.travel.factory.DatabaseConnectionFactor;
import cn.corechan.travel.json.Status;

import java.sql.SQLException;

public class UserGoodDAOProxy implements IUserGoodDAO {
    private DatabaseConnection dbc = null;
    private IUserGoodDAO userGoodDAO = null;

    public UserGoodDAOProxy() throws ClassNotFoundException, SQLException {
        dbc = DatabaseConnectionFactor.getMySQLDatabaseConnection();
        userGoodDAO = new UserGoodDAOImpl(dbc.getConnection());
    }

    @Override
    public Status findLove(String phoneNumber) throws SQLException {
        Status status;
        try {
            status = userGoodDAO.findLove(phoneNumber);
        } catch (SQLException e) {
            throw e;
        } finally {
            dbc.close();
        }

        return status;
    }
}
