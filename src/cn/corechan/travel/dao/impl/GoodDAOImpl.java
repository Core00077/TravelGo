package cn.corechan.travel.dao.impl;

import cn.corechan.travel.dao.IGoodDAO;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.Good;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodDAOImpl implements IGoodDAO {
    private Connection conn = null;

    public GoodDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Status addLove(String Id) throws SQLException {
        // 初始化添加失败
        Status addStatus = new Status();
        addStatus.setStatus("systemError");

        // 收藏商品
//        String sql = "INSERT INTO travelgo.usergood (phonenumber,goodId) VALUES(?,?)";
//        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
//            pstmt.setString(1, user.getPhoneNumber());
//            pstmt.setString(2, user.getName());
//            if (pstmt.executeUpdate() > 0) {
//                status.setContent("success","");
//            }
//        } catch (SQLException e) {
//            throw e;
//        }


        return addStatus;
    }

    @Override
    public Status findById(String Id) throws SQLException {
        // 初始化为查询失败
        Status status = new Status();
        status.setContent("goodNotExist","");
        status.setData(null);

        // 查询
        String queryGood = "SELECT name,price,city,route,description,comment FROM good WHERE Id=?";
        String queryPictures = "SELECT pictureURL FROM goodpicture WHERE goodId=?";
        try (PreparedStatement pstmtGood = conn.prepareStatement(queryGood);
                PreparedStatement pstmtPictures = conn.prepareStatement(queryPictures)) {
            pstmtGood.setString(1, Id);
            pstmtPictures.setString(1, Id);
            try (ResultSet rsetGood = pstmtGood.executeQuery();
                ResultSet rsetPictures = pstmtPictures.executeQuery()) {
                Good good = new Good();

                // 添加商品的基本信息
                if (rsetGood.next()) {          // 查询成功
                    good.setGoodId(Id);
                    good.setPrice(rsetGood.getDouble(2));

                    try {
                        String str = rsetGood.getString(3);
                        good.setCity(URLEncoder.encode(str, "UTF-8"));
                        str = rsetGood.getString(1);
                        good.setName(URLEncoder.encode(str, "UTF-8"));
                        str = rsetGood.getString(4);
                        good.setRoute(URLEncoder.encode(str, "UTF-8"));
                        str = rsetGood.getString(5);
                        good.setDescription(URLEncoder.encode(str, "UTF-8"));
                        str = rsetGood.getString(6);
                        good.setComment(URLEncoder.encode(str, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                    }
                }

                // 添加商品的图片
                List<String> pictures = new ArrayList<>();
                while (rsetPictures.next()) {
                    pictures.add(rsetPictures.getString("pictureURL"));
                }
                good.setPictures(pictures);

                // 添加查询结果到状态
                status.setContent("success","");     // 更改状态码
                status.setData(good);
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
        // 初始化查询失败
        Status findStatus = new Status();
        findStatus.setContent("goodNotExist","");
        findStatus.setData(null);

        // 查询
        String queryGood = "SELECT Id,name,price,route FROM good WHERE city LIKE ?" +
                "OR comment LIKE ? OR name LIKE ? OR route LIKE ? OR  description LIKE ?";
        String queryPictures = "SELECT pictureURL FROM goodpicture WHERE goodId=?";
        try (PreparedStatement pstmtGood = conn.prepareStatement(queryGood);
                PreparedStatement pstmtPictures = conn.prepareStatement(queryPictures)) {
            pstmtGood.setString(1,"%"+city+"%");
            pstmtGood.setString(2,"%"+city+"%");
            pstmtGood.setString(3,"%"+city+"%");
            pstmtGood.setString(4,"%"+city+"%");
            pstmtGood.setString(5,"%"+city+"%");
            try (ResultSet rsetGood = pstmtGood.executeQuery()) {
                List<Good> goods = new ArrayList<>();
                while(rsetGood.next()) {
                    Good good = new Good();
                    String goodId = rsetGood.getString(1);
                    good.setGoodId(goodId);
                    good.setName(rsetGood.getString(2));
                    good.setPrice(rsetGood.getDouble(3));
                    good.setRoute(rsetGood.getString(4));

                    try (ResultSet rsetPictures = pstmtPictures.executeQuery()) {
                        List<String> picture = new ArrayList<>();
                        if (rsetPictures.next()) {
                            picture.add(rsetPictures.getString("pictureURL"));
                        }
                        good.setPictures(picture);
                    } catch (SQLException e) {
                        throw e;
                    }
                }
                findStatus.setStatus("success");
                findStatus.setData(goods);
            } catch (SQLException e) {
                throw e;
            }
        } catch (SQLException e) {
            throw e;
        }

        return findStatus;
    }

    @Override
    public Status findAll() throws SQLException {
        // 初始化为查询失败
        Status status = new Status();
        status.setContent("goodNotExist","");
        status.setData(null);

        // 查询goodId
        String queryId = "SELECT Id FROM good";
        try (PreparedStatement pstmtId = conn.prepareStatement(queryId)) {
            try (ResultSet rsetId = pstmtId.executeQuery()) {
                List<Good> goods = new ArrayList<>();
                while (rsetId.next()) {
                    String goodId = rsetId.getString("Id");
                    Good good = (Good)findById(goodId).getData();
                    goods.add(good);
                }

                // 添加查询结果到状态
                status.setContent("success","");     // 更改状态码
                status.setData(goods);
            } catch (SQLException e) {
                throw e;
            }
        } catch (SQLException e) {
            throw e;
        }
        return status;
    }
}
