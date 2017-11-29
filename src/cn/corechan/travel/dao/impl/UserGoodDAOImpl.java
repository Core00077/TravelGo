package cn.corechan.travel.dao.impl;

import cn.corechan.travel.dao.IUserGoodDAO;
import cn.corechan.travel.json.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserGoodDAOImpl implements IUserGoodDAO {
    private Connection conn = null;

    public UserGoodDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Status findLoveIds(String phoneNumber) throws SQLException {
        // 初始化为查询失败
        Status status = new Status();
        status.setContent("noLove", "");
        status.setData(null);

        // 查询收藏夹内容
        String loveQuery = "SELECT goodId FROM usergood WHERE phoneNumber=?";
        try (PreparedStatement pstmt = conn.prepareStatement(loveQuery)) {
            pstmt.setString(1, phoneNumber);
            try (ResultSet rset = pstmt.executeQuery()) {
                List<String> goodIds = new ArrayList<>();
                while (rset.next()) {
                    goodIds.add((String)rset.getString("goodId"));
                }
                status.setContent("success", "");
                status.setData(goodIds);
            } catch (SQLException e) {
                throw e;
            }
        } catch (SQLException e) {
            throw e;
        }

        return status;
    }
}
