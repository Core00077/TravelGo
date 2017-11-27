package cn.corechan.travel.dao.impl;

import cn.corechan.travel.dao.IUserDAO;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements IUserDAO {
    private Connection conn = null;

    public UserDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean doCreate(User user) throws SQLException {
        boolean flag = false;
        String sql = "INSERT INTO travelgo.usertable (phonenumber,name,pwd) VALUES(?,?,?)";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getPhoneNumber());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPwd());
            if (pstmt.executeUpdate() > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            throw e;
        }
        return flag;
    }

    @Override
    public boolean doChange() throws SQLException {
        return false;
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) throws SQLException {
        User user = null;
        String sql = "SELECT phonenumber,name,pwd,sex,realname,hometown FROM usertable WHERE phonenumber=?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, phoneNumber);
            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    user = new User();
                    user.setPhoneNumber(phoneNumber);
                    user.setName(rset.getString(2));
                    user.setPwd(rset.getString(3));
                    user.setSex(rset.getString(4));
                    user.setRealName(rset.getString(5));
                    user.setRealName(rset.getString(6));
                }
            } catch (SQLException e) {
                throw e;
            }
        } catch (SQLException e) {
            throw e;
        }
        return user;
    }
}
