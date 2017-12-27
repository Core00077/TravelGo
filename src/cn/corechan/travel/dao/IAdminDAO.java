package cn.corechan.travel.dao;

import cn.corechan.travel.json.Status;

import java.sql.SQLException;

public interface IAdminDAO {
    Status loginAdmin(String phoneNumber, String pwd) throws SQLException;

    Status findCertificates() throws SQLException;

    Status setCertificateStatus(String phoneNumber, int s,String msg) throws SQLException;
}
