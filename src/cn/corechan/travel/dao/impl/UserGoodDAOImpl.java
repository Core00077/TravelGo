package cn.corechan.travel.dao.impl;

import cn.corechan.travel.dao.IUserGoodDAO;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.Good;
import com.sun.org.apache.regexp.internal.RE;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
        String loveIDQuery = "SELECT goodId FROM usergood WHERE phoneNumber=?";
        String loveQuery = "SELECT * FROM good WHERE Id=?";
        String pictureQuery = "SELECT pictureURL FROM goodpicture WHERE goodId=?";
        try (PreparedStatement pstmt = conn.prepareStatement(loveIDQuery)) {
            pstmt.setString(1, phoneNumber);
            try (ResultSet rset = pstmt.executeQuery()) {
                ArrayList<Good> goods = new ArrayList<>();
                while (rset.next()) {
                    String goodId = rset.getString("goodId");
                    try (PreparedStatement p = this.conn.prepareStatement(loveQuery)) {
                        p.setString(1, goodId);
                        try (ResultSet goodSet = p.executeQuery()) {
                            while (goodSet.next()) {
                                Good good = new Good();
                                good.setId(goodSet.getInt("Id"));
                                good.setName(URLEncoder.encode(goodSet.getString("name"), "UTF-8"));
                                good.setPrice(goodSet.getDouble("price"));
                                good.setRoute(URLEncoder.encode(goodSet.getString("route"), "UTF-8"));
                                List<String> urls = new ArrayList<>();
                                try (PreparedStatement url = this.conn.prepareStatement(pictureQuery)) {
                                    url.setString(1, goodId);
                                    try (ResultSet urlSet = url.executeQuery()) {
                                        while (urlSet.next()) {
                                            urls.add(urlSet.getString("pictureURL"));
                                        }
                                    }
                                }
                                good.setPictures(urls);
                                goods.add(good);
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                status.setContent("success", "love query OK!");
                status.setData(goods);
            }
        } catch (SQLException e) {
            status.setContent("SQLError", e.toString());
            System.out.println(e.toString());
        }
        return status;
    }

    @Override
    public Status AddLove(String phoneNumber, int goodId) throws SQLException {
        Status status = new Status();
        status.setContent("fail", "");
        status.setData(null);
        status = this.isLove(phoneNumber, goodId);
        if (status.getStatus().equals("isLove")) {
            return status;
        }
        if (status.getStatus().equals("noLove")) {
            String addLoveQuery = "INSERT INTO travelgo.usergood (phonenumber, goodId)  VALUES (?,?)";
            try (PreparedStatement pst = conn.prepareStatement(addLoveQuery)) {
                pst.setString(1, phoneNumber);
                pst.setInt(2, goodId);
                if (pst.executeUpdate() > 0) {
                    status.setContent("success", "Successfully Added");
                }
            }
        }
        return status;
    }

    @Override
    public Status isLove(String phoneNumber, int goodId) throws SQLException {
        Status status = new Status();
        status.setContent("fail", "");
        status.setData(null);

        status=new GoodDAOImpl(conn).findById(goodId);
        if(status.getStatus().equals("goodNotExist"))
            return status;
        String isLoveQuery = "SELECT * FROM travelgo.usergood WHERE phonenumber=? AND goodId=?";
        try (PreparedStatement pst = conn.prepareStatement(isLoveQuery)) {
            pst.setString(1, phoneNumber);
            pst.setInt(2, goodId);
            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next())
                    status.setContent("isLove", "goodId: " + goodId + "is in collection!");
                else
                    status.setContent("noLove", "goodId: " + goodId + "is NOT in collection!");
            }
        }
        return status;
    }

    @Override
    public Status DeleteLove(String phoneNumber, int goodId) throws SQLException {
        Status status = new Status();
        status.setContent("fail", "");
        status.setData(null);
        String deleteLoveQuery = "DELETE FROM travelgo.usergood WHERE phonenumber=? AND goodId=?";
        try (PreparedStatement pst = this.conn.prepareStatement(deleteLoveQuery)) {
            pst.setString(1, phoneNumber);
            pst.setInt(2, goodId);
            if (pst.executeUpdate() > 0) {
                status.setContent("success", "Deleted successfully!");
            }
        }
        return status;
    }

    @Override
    public Status DeleteLoves(String phoneNumber, ArrayList<Integer> goodIds) throws SQLException {
        Status status = new Status();
        String deleteLoveQuery = "DELETE FROM travelgo.usergood WHERE phonenumber=? AND goodId=?";
        try (PreparedStatement pst = conn.prepareStatement(deleteLoveQuery)) {
            for (int goodId : goodIds) {
                pst.setString(1, phoneNumber);
                pst.setInt(2, goodId);
                pst.addBatch();
            }
            int[] result = pst.executeBatch();
            int resultSum=0;
            ArrayList<Integer> errorResult=new ArrayList<>();
            for (int i = 0; i < result.length; i++) {
                if(result[i]>0){
                    resultSum++;
                }
                else{
                    errorResult.add(goodIds.get(i));
                    System.out.println(goodIds.get(i)+"删除错误");
                }
            }
            if(resultSum==result.length){
                status.setContent("success","Deleted successfully!");
            }
            else {
                status.setContent("fail","");
                status.setData(errorResult);
            }
        }
        return status;
    }
}
