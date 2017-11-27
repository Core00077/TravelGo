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
        dbc = DatabaseConnectionFactor.getMySQLDatabaseConnection();
        userDAO = new UserDAOImpl(dbc.getConnection());
    }

    @Override
    public Status doRegister(User user) throws SQLException {
        // 初始化为注册失败
        Status status = null;

        try {
            if (userDAO.findByPhoneNumber(user.getPhoneNumber()) == null) {
                status = userDAO.doRegister(user);
            } else {            // 手机号已存在
                status = new Status();
                status.setContent("phoneHasExisted","");
                status.setData(null);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            dbc.close();
        }
        return status;
    }

    @Override
    public Status doChange(User user) throws SQLException {
        Status status;
        try {
            status = userDAO.doChange(user);
        } catch (SQLException e) {
            throw e;
        } finally {
            dbc.close();
        }

        return status;
    }

    @Override
    public Status findByPhoneNumber(String phoneNumber) throws SQLException {
        Status status;
        try {
            status = userDAO.findByPhoneNumber(phoneNumber);
        } catch (SQLException e) {
            throw e;
        } finally {
            dbc.close();
        }
        return status;
    }

    public Status doLogin(String phoneNumber, String pwd) throws SQLException {
        Status status;

        try {
            status = userDAO.findByPhoneNumber(phoneNumber);    // 根据手机号查找用户
        } catch (SQLException e) {
            throw e;
        } finally {
            dbc.close();
        }

        // 手机号存在
        if (status.getStatus().equals("phoneExist")) {
            if (((User)status.getData()).getPwd().equals(pwd)) {    // 密码相等，登录成功
                status.setContent("success","");
            } else {        // 否则失败
                status.setData(null);
                status.setContent("passwordWrong","");
            }
        }

        return status;
    }

    @Override
    public Status doDelete(String phoneNumber) throws SQLException {
        return null;
    }
}
