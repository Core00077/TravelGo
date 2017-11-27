package cn.corechan.travel.dao.impl;

import cn.corechan.travel.dao.IUserDAO;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.User;

import java.sql.*;

public class UserDAOImpl implements IUserDAO {
    private Connection conn = null;

    public UserDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Status doRegister(User user) throws SQLException {
        // 初始化为注册失败
        Status status = new Status();
        status.setContent("failed","");
        status.setData(null);

        // 注册用户
        String sql = "INSERT INTO travelgo.usertable (phonenumber,name,pwd) VALUES(?,?,?)";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getPhoneNumber());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPwd());
            if (pstmt.executeUpdate() > 0) {
                status.setContent("success","");
                status.setData(user);
            }
        } catch (SQLException e) {
            throw e;
        }
        return status;
    }

    @Override
    public Status doChange(User newUser) throws SQLException {
        // 修改失败
        Status status = new Status();
        status.setContent("failed","");
        status.setData(null);

        // 修改用户数据
        String query = "UPDATE usertable SET name=?, realname=?," +
                        "sex=?, hometown=?  WHERE phonenumber=?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newUser.getName());
            pstmt.setString(2, newUser.getRealName());
            pstmt.setString(3, newUser.getSex());
            pstmt.setString(4, newUser.getHometown());
            pstmt.setString(5, newUser.getPhoneNumber());
            if (pstmt.executeUpdate() > 0) {
                status.setContent("success", "");
            }
        } catch (SQLException e) {
            throw e;
        }
        return status;
    }

    @Override
    public Status findByPhoneNumber(String phoneNumber) throws SQLException {
        // 初始化为查询失败
        Status status = new Status();
        status.setContent("phoneNotExist","");
        status.setData(null);

        // 查询
        String sql = "SELECT name,pwd,sex,realname,hometown FROM usertable WHERE phonenumber=?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, phoneNumber);
            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {              // 查询成功
                    User user = new User();
                    user.setPhoneNumber(phoneNumber);
                    user.setName(rset.getString(1));
                    user.setPwd(rset.getString(2));
                    user.setSex(rset.getString(3));
                    user.setRealName(rset.getString(4));
                    user.setHometown(rset.getString(5));

                    status.setContent("success","");     // 更改状态码
                    status.setData(user);
                }
            } catch (SQLException e) {
                throw e;
            }
        } catch (SQLException e) {
            throw e;
        }
        return status;
    }

    @Override
    public Status doDelete(String phoneNumber) throws SQLException {
        return null;
    }
}
