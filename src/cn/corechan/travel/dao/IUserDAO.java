package cn.corechan.travel.dao;

import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.vo.Certificate;
import cn.corechan.travel.vo.Contact;
import cn.corechan.travel.vo.User;

import java.sql.SQLException;

public interface IUserDAO {
    Status doRegister(User user) throws SQLException;

    Status doChange(User newUser) throws SQLException;

    Status findByPhoneNumber(String phoneNumber) throws SQLException;

    Status findCertificate(String phoneNumber) throws SQLException;

    Status doCertificate(Certificate certificate) throws SQLException;

    Status checkCertificate(String phoneNumber) throws SQLException;

    Status addContact(String phoneNumber, Contact contact) throws SQLException;

    Status findContacts(String phoneNumber) throws SQLException;
}
