package cn.corechan.travel.dao.proxy;

import cn.corechan.travel.dao.IAdminDAO;
import cn.corechan.travel.dao.impl.AdminDAOImpl;
import cn.corechan.travel.dbc.DatabaseConnection;
import cn.corechan.travel.dbc.impl.MySQLDatabaseConnection;
import cn.corechan.travel.factory.DatabaseConnectionFactor;
import cn.corechan.travel.json.Status;

import java.sql.SQLException;

public class AdminDAOProxy implements IAdminDAO {
    private DatabaseConnection dbc = null;
    private IAdminDAO adminDAO = null;


    public AdminDAOProxy() throws SQLException, ClassNotFoundException {
        this.dbc = DatabaseConnectionFactor.getMySQLDatabaseConnection();
        adminDAO = new AdminDAOImpl(this.dbc.getConnection());
    }

    @Override
    public Status loginAdmin(String phoneNumber, String pwd) throws SQLException {

        try {
            return adminDAO.loginAdmin(phoneNumber, pwd);
        } finally {
            dbc.close();
        }
    }

    @Override
    public Status findCertificates() throws SQLException {
        try {
            return adminDAO.findCertificates();
        } finally {
            dbc.close();
        }
    }

    @Override
    public Status setCertificateStatus(String phoneNumber, int s, String msg) throws SQLException {
        try {
            return adminDAO.setCertificateStatus(phoneNumber, s, msg);
        } finally {
            dbc.close();
        }
    }
}
