package cn.corechan.travel.dao.impl;

import cn.corechan.travel.dao.IAdminDAO;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.Certificate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements IAdminDAO {
    private Connection conn = null;

    public AdminDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Status loginAdmin(String phoneNumber, String pwd) throws SQLException {
        Status status = new Status();
        String querySQL = "SELECT * FROM admintable WHERE phonenumber=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(querySQL)) {
            preparedStatement.setString(1, phoneNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    if (pwd.equals(resultSet.getString(2))) {
                        status.setContent("success", "AdminLogin successfully!");
                    } else
                        status.setContent("passwordWrong", "Password is Wrong!");
                } else
                    status.setContent("phoneNotExist", "phone Number is NOT exist!");
            }
        }
        return status;
    }

    @Override
    public Status findCertificates() throws SQLException {
        Status status = new Status();
        String querySQL = "SELECT * FROM certificate WHERE status=1";
        List<Certificate> certificates = new ArrayList<>();
        try (ResultSet resultSet = conn.createStatement().executeQuery(querySQL)) {
            while (resultSet.next()) {
                String phoneNumber = resultSet.getString(1);
                String ID = resultSet.getString(2);
                String realname = resultSet.getString(3);
                String contact = resultSet.getString(4);
                String address = resultSet.getString(5);
                String picURL = resultSet.getString(6);
                int s = resultSet.getInt(7);
                String msg = resultSet.getString(8);
                Certificate certificate = new Certificate(phoneNumber, ID, realname, contact, address, picURL, s, msg);
                certificates.add(certificate);
            }
            status.setData(certificates);
            status.setContent("success", "all certificates that are being verified");
        }
        return status;
    }

    @Override
    public Status setCertificateStatus(String phoneNumber, int s,String msg) throws SQLException {
        Status status = new Status();
        String changeSQL = "UPDATE certificate SET status=?, msg=? WHERE phonenumber=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(changeSQL)) {
            preparedStatement.setInt(1, s);
            preparedStatement.setString(2,msg);
            preparedStatement.setString(3, phoneNumber);
            if (preparedStatement.executeUpdate() > 0) {
                status.setContent("success", "update successfully!");
            }
            else
                status.setContent("phoneNotExist","can not find phone number!");
        }
        return status;
    }
}
