package cn.corechan.travel.dao.impl;

import cn.corechan.travel.dao.IGoodDAO;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.Good;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodDAOImpl implements IGoodDAO {
    private Connection conn = null;

    public GoodDAOImpl(Connection conn) {
        this.conn = conn;
    }


//    @Override
//    public Status doCreate(Good good) throws SQLException {
//        return null;
//    }

    @Override
    public Status findById(String Id) throws SQLException {
        // 初始化为查询失败
        Status status = new Status();
        status.setContent("goodNotExist","");
        status.setData(null);

        // 查询
        String query = "SELECT name,price,city,route,picture,description,comment FROM good WHERE Id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,Id);
            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {          // 查询成功
                    Good good = new Good();
                    good.setId(Id);
                    good.setName(rset.getString(1));
                    good.setPrice(rset.getDouble(2));
                    good.setCity(rset.getString(3));
                    good.setRoute(rset.getString(4));
                    good.setPicture(rset.getString(5));
                    good.setDescription(rset.getString(6));
                    good.setComment(rset.getString(7));

                    status.setContent("success","");     // 更改状态码
                    status.setData(good);
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
    public Status findByCity(String city) throws SQLException {
        return null;
    }
}
