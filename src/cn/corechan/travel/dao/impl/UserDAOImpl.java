package cn.corechan.travel.dao.impl;

import cn.corechan.travel.dao.IUserDAO;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.vo.Certificate;
import cn.corechan.travel.vo.Contact;
import cn.corechan.travel.vo.Good;
import cn.corechan.travel.vo.User;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDAOImpl implements IUserDAO {
    private Connection conn;

    public UserDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Status doRegister(User user) throws SQLException {
        // 初始化为注册失败
        Status status = new Status();
        status.setContent("failed", "");
        status.setData(null);

        // 注册用户
        String sql = "INSERT INTO travelgo.usertable (phonenumber,name,pwd) VALUES(?,?,?)";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getPhoneNumber());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPwd());
            if (pstmt.executeUpdate() > 0) {
                status.setContent("success", "");
            }
        }
        return status;
    }

    @Override
    public Status doChange(User newUser) throws SQLException {
        // 修改失败
        Status status = new Status();
        status.setContent("failed", "");
        status.setData(null);

        // 修改用户数据
        String query = "UPDATE usertable SET name=?, realname=?," +
                "sex=?, hometown=?, introduction=?, travelgo.usertable.headPicture=?  WHERE phonenumber=?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newUser.getName());
            String realName = newUser.getRealName();
            String hometown = newUser.getHometown();
            String introduction = newUser.getIntroduction();
            if (realName != null)
                pstmt.setString(2, URLDecoder.decode(realName, "UTF-8"));
            else
                pstmt.setString(2, null);
            pstmt.setString(3, newUser.getSex());
            if (hometown != null)
                pstmt.setString(4, URLDecoder.decode(hometown, "UTF-8"));
            else
                pstmt.setString(4, null);
            if (introduction != null)
                pstmt.setString(5, URLDecoder.decode(introduction, "UTF-8"));
            else
                pstmt.setString(5, null);
            pstmt.setString(6, newUser.getHeadPicture());
            pstmt.setString(7, newUser.getPhoneNumber());
            if (pstmt.executeUpdate() > 0) {
                status.setContent("success", "");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public Status findByPhoneNumber(String phoneNumber) throws SQLException {
        // 初始化为查询失败
        Status status = new Status();
        status.setContent("phoneNotExist", "");
        status.setData(null);

        // 查询
        String sql = "SELECT name,pwd,sex,realname,hometown,introduction,headPicture FROM usertable WHERE phonenumber=?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, phoneNumber);
            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {              // 查询成功
                    User user = new User();
                    user.setPhoneNumber(phoneNumber);
                    user.setName(rset.getString(1));
                    user.setPwd(rset.getString(2));
                    user.setSex(rset.getString(3));
                    try {
                        String realName = rset.getString(4);
                        String hometown = rset.getString(5);
                        String introduction = rset.getString(6);
                        if (realName != null) {
                            user.setRealName(URLEncoder.encode(realName, "UTF-8"));
                        }
                        if (hometown != null) {
                            user.setHometown(URLEncoder.encode(hometown, "UTF-8"));
                        }
                        if (introduction != null) {
                            user.setIntroduction(URLEncoder.encode(introduction, "UTF-8"));
                        }
                        user.setHeadPicture(rset.getString(7));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    user.setUserContacts((List<Contact>) findContacts(phoneNumber).getData());
                    status.setContent("success", "");     // 更改状态码
                    status.setData(user);
                }
            }
        }
        return status;
    }

    public Status findBySeller(String phoneNumber) throws SQLException {
        String findGoodsSQL = "SELECT Id, good.name,price,pubtime,seller,usertable.name,sex,introduction,headPicture,status FROM good JOIN usertable ON seller=usertable.phonenumber WHERE seller=?";
        String findGoodPicSQL = "SELECT pictureURL FROM goodpicture WHERE goodId=? LIMIT 1;";
        List<Good> goods = new ArrayList<>();
        Status status = new Status();
        try (PreparedStatement preparedStatement = conn.prepareStatement(findGoodsSQL)) {
            preparedStatement.setString(1, phoneNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    if(resultSet.getInt("status")==0){
                        continue;
                    }
                    Good good = new Good();
                    good.setId(resultSet.getString("Id"));
                    good.setName(URLEncoder.encode(resultSet.getString("good.name"), "UTF-8"));
                    good.setPrice(Double.parseDouble(resultSet.getString("price")));
                    good.setPubtime(resultSet.getString("pubtime"));
                    try (PreparedStatement preparedStatement1 = conn.prepareStatement(findGoodPicSQL)) {
                        preparedStatement1.setString(1, good.getId());
                        try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
                            if (resultSet1.next()) {
                                List<String> pic = new ArrayList<>();
                                pic.add(resultSet1.getString("pictureURL"));
                                good.setPictures(pic);
                            }
                        }
                    }
                    HashMap<String, String> map = new HashMap<>();
                    map.put("phoneNumber", resultSet.getString("seller"));
                    map.put("name", resultSet.getString("usertable.name"));
                    map.put("headPicture", resultSet.getString("headPicture"));
                    map.put("sex", resultSet.getString("sex"));
                    if (resultSet.getString("introduction") != null)
                        map.put("introduction", URLEncoder.encode(resultSet.getString("introduction"), "UTF-8"));
                    good.setSeller(map);
                    goods.add(good);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            status.setData(goods);
            status.setContent("success", "seller goods found successfully!");
        }
        return status;
    }

    @Override
    public Status findCertificate(String phoneNumber) throws SQLException {
        String query = "SELECT * FROM certificate WHERE phonenumber=?";
        Status status = new Status();
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, phoneNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String ID = resultSet.getString(2);
                    String realname = resultSet.getString(3);
                    String contact = resultSet.getString(4);
                    String address = resultSet.getString(5);
                    String picURL = resultSet.getString(6);
                    int s = resultSet.getInt(7);
                    String msg = resultSet.getString(8);
                    if (msg != null)
                        msg = URLEncoder.encode(msg, "UTF-8");
                    Certificate certificate = new Certificate(phoneNumber, ID, URLEncoder.encode(realname, "UTF-8"), contact,
                            URLEncoder.encode(address, "UTF-8"), picURL, s, msg);
                    switch (s) {
                        case 0:
                            status.setContent("unpassed", "Certificate not passed!");
                            status.setData(certificate);
                            break;
                        case 1:
                            status.setContent("verifying", "Verifying, please wait.");
                            status.setData(certificate);
                            break;
                        case 2:
                            status.setContent("passed", "Find successfully");
                            status.setData(certificate);
                            break;
                    }
                } else {
                    status.setContent("IDNotExist", "Certificate not exist!");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    @Override
    public Status doCertificate(Certificate certificate) throws SQLException {
        Status status = checkCertificate(certificate.getPhoneNumber());
        if (status.getStatus().equals("passed") || status.getStatus().equals("verifying")) {
            status.setData(null);
            return status;
        }
        if (status.getStatus().equals("IDNotExist")) {
            String insertSQL = "INSERT INTO certificate(phonenumber, ID, realname, contact, address, ID_in_hand_picURL, status, msg) VALUES (?,?,?,?,?,?,?,?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, certificate.getPhoneNumber());
                preparedStatement.setString(2, certificate.getID());
                preparedStatement.setString(3, certificate.getRealname());
                preparedStatement.setString(4, certificate.getContact());
                preparedStatement.setString(5, certificate.getAddress());
                preparedStatement.setString(6, certificate.getIDpicURL());
                preparedStatement.setInt(7, certificate.getStatus());
                preparedStatement.setString(8, certificate.getMsg());

                if (preparedStatement.executeUpdate() > 0) {
                    status.setContent("success", "");
                }
            }
        } else {
            String updateSQL = "UPDATE certificate SET ID=?,realname=?,contact=?,address=?,ID_in_hand_picURL=?,status=?,msg=? WHERE travelgo.certificate.phonenumber=?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
                preparedStatement.setString(1, certificate.getID());
                preparedStatement.setString(2, certificate.getRealname());
                preparedStatement.setString(3, certificate.getContact());
                preparedStatement.setString(4, certificate.getAddress());
                preparedStatement.setString(5, certificate.getIDpicURL());
                preparedStatement.setInt(6, certificate.getStatus());
                preparedStatement.setString(7, certificate.getMsg());
                preparedStatement.setString(8,certificate.getPhoneNumber());

                if (preparedStatement.executeUpdate() > 0) {
                    status.setContent("success", "");
                    status.setData(null);
                }
            }
        }
        return status;
    }

    @Override
    public Status checkCertificate(String phoneNumber) throws SQLException {
        Status status = new Status();
        String checkSQL = "SELECT * FROM certificate_check WHERE phonenumber=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(checkSQL)) {
            preparedStatement.setString(1, phoneNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int s = resultSet.getInt("status");
                    String msg = resultSet.getString("msg");
                    switch (s) {
                        case 0:
                            status.setStatus("unpassed");
                            break;
                        case 1:
                            status.setStatus("verifying");
                            break;
                        case 2:
                            status.setStatus("passed");
                            break;
                    }
                    status.setData(msg);
                } else
                    status.setStatus("IDNotExist");
            }
        }
        return status;
    }

    @Override
    public Status addContact(String phoneNumber, Contact contact) throws SQLException {
        Status status = new Status();
        if (checkContact(phoneNumber, contact)) {
            status.setContent("ContactExists", "Contact already exists!");
            return status;
        }
        String contactInsertSQL = "INSERT INTO user_contacts(userId, name, phonenumber) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(contactInsertSQL)) {
            preparedStatement.setString(1, phoneNumber);
            preparedStatement.setString(2, contact.getName());
            preparedStatement.setString(3, contact.getPhoneNumber());
            if (preparedStatement.executeUpdate() > 0) {
                status.setContent("success", "Contact added successfully!");
            }
        }
        return status;
    }

    public boolean checkContact(String phoneNumber, Contact contact) throws SQLException {
        String findContactSQL = "SELECT * FROM user_contacts WHERE userId=? AND name=? AND phonenumber=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(findContactSQL)) {
            preparedStatement.setString(1, phoneNumber);
            preparedStatement.setString(2, contact.getName());
            preparedStatement.setString(3, contact.getPhoneNumber());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public Status findContacts(String phoneNumber) throws SQLException {
        Status status = new Status();
        String findContactSQL = "SELECT * FROM user_contacts WHERE userId=?";
        List<Contact> userContacts = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(findContactSQL)) {
            preparedStatement.setString(1, phoneNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                    userContacts.add(new Contact(URLEncoder.encode(resultSet.getString("name"), "UTF-8"), URLEncoder.encode(resultSet.getString("phonenumber"), "UTF-8")));
                status.setData(userContacts);
                status.setContent("success", "find user contacts successfully!");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return status;
    }


}
