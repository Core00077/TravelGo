package cn.corechan.travel.dao.proxy;

import cn.corechan.travel.dao.IUserDAO;
import cn.corechan.travel.dao.impl.UserDAOImpl;
import cn.corechan.travel.dbc.DatabaseConnection;
import cn.corechan.travel.factory.DatabaseConnectionFactor;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.User;

import java.sql.SQLException;

public class UserDAOProxy implements IUserDAO {
    private DatabaseConnection dbc = null;
    private IUserDAO userDAO = null;

    public UserDAOProxy() throws ClassNotFoundException, SQLException {
        this.dbc = DatabaseConnectionFactor.getMySQLDatabaseConnection();
        this.userDAO = new UserDAOImpl(this.dbc.getConnection());
    }

    @Override
    public boolean doCreate(User user) throws SQLException {
        boolean flag = false;
        try {
            if (userDAO.findByPhoneNumber(user.getPhoneNumber()) == null) {
                flag = userDAO.doCreate(user);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            dbc.close();
        }
        return flag;
    }

    @Override
    public boolean doChange() throws SQLException {
        return false;
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) throws SQLException {
        User user;
        try {
            user = userDAO.findByPhoneNumber(phoneNumber);
        } catch (SQLException e) {
            throw e;
        } finally {
            dbc.close();
        }
        return user;
    }

    public User doLogin(String phoneNumber, String pwd) throws SQLException {
        Status status;
        User user;

        try {
            user = userDAO.findByPhoneNumber(phoneNumber);
            if (user == null) {
                return null;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            dbc.close();
        }
        if (user.getPwd().equals(pwd)) {
            return user;
        }
        return null;
    }
}
