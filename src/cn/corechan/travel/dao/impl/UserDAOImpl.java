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
        status.setContent("registerFailed","");
        status.setData(null);

        // 注册用户
        String sql = "INSERT INTO travelgo.usertable (phonenumber,name,pwd) VALUES(?,?,?)";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getPhoneNumber());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPwd());
            if (pstmt.executeUpdate() > 0) {
                status.setContent("registerSuccess","");
                status.setData(user);
            }
        } catch (SQLException e) {
            throw e;
        }
        return status;
    }

    @Override
    public Status doChange(User user) throws SQLException {
        // 修改失败
        Status status = new Status();
        status.setContent("changeFailed","");
        status.setData(null);

        // 首先获取原来的信息
        User oldUser;
        try {
            oldUser = (User)findByPhoneNumber(user.getPhoneNumber()).getData();
        } catch (SQLException e) {
            throw e;
        }

        // 修改用户数据
        String query = "SELECT * FROM usertable WHERE phonenumber=?";
        try (PreparedStatement pstmt = conn.prepareStatement(query,
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
            pstmt.setString(1, user.getPhoneNumber());
            try (ResultSet rset = pstmt.executeQuery()) {
                rset.updateString("name", user.getName());
                rset.updateString("realname", user.getRealName());
                rset.updateString("sex", user.getSex());
                rset.updateString("hometown", user.getHometown());
                rset.updateRow();
                status.setContent("success","");
                status.setData(user);
            } catch (SQLException e) {
                status.setData(oldUser);
                throw e;
            }
        } catch (SQLException e) {
            status.setData(oldUser);
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
        String sql = "SELECT phonenumber,name,pwd,sex,realname,hometown FROM usertable WHERE phonenumber=?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, phoneNumber);
            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {              // 查询成功
                    User user = new User();
                    user.setPhoneNumber(phoneNumber);
                    user.setName(rset.getString(2));
                    user.setPwd(rset.getString(3));
                    user.setSex(rset.getString(4));
                    user.setRealName(rset.getString(5));
                    user.setRealName(rset.getString(6));

                    status.setContent("phoneExist","");     // 更改状态码
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
