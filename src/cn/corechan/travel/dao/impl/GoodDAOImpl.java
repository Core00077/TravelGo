package cn.corechan.travel.dao.impl;

import cn.corechan.travel.dao.IGoodDAO;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.Good;
import com.mysql.jdbc.Statement;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodDAOImpl implements IGoodDAO {
    private Connection conn;

    public GoodDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Status findById(String Id) throws SQLException {
        // 初始化为查询失败
        Status status = new Status();
        status.setContent("goodNotExist", "");
        status.setData(null);
        // 查询
        String queryGood = "SELECT name,price,city,route,description,comment,seller FROM good WHERE Id=?";
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
                    good.setId(Id);
                    good.setPrice(rsetGood.getDouble(2));

                    try {
                        String str = rsetGood.getString(3);
                        if (str != null)
                            good.setCity(URLEncoder.encode(str, "UTF-8"));
                        str = rsetGood.getString(1);
                        if (str != null)
                            good.setName(URLEncoder.encode(str, "UTF-8"));
                        str = rsetGood.getString(4);
                        if (str != null)
                            good.setRoute(URLEncoder.encode(str, "UTF-8"));
                        str = rsetGood.getString(5);
                        if (str != null)
                            good.setDescription(URLEncoder.encode(str, "UTF-8"));
                        str = rsetGood.getString(6);
                        if (str != null)
                            good.setComment(URLEncoder.encode(str, "UTF-8"));
                        str = rsetGood.getString(7);
                        if (str != null)
                            good.setSeller(URLEncoder.encode(str, "UTF-8"));
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }

                    // 添加商品的图片
                    List<String> pictures = new ArrayList<>();
                    while (rsetPictures.next()) {
                        pictures.add(rsetPictures.getString("pictureURL"));
                    }
                    good.setPictures(pictures);

                    // 添加查询结果到状态
                    status.setContent("success", "");     // 更改状态码
                    status.setData(good);
                }
            }
        }
        return status;
    }

    @Override
    public Status findByCity(String city) throws SQLException {
        // 初始化查询失败
        Status findStatus = new Status();
        findStatus.setContent("goodNotExist", "");
        findStatus.setData(null);

        // 查询
        String queryGood = "SELECT Id,name,price,route FROM good WHERE city LIKE ?  " +
                " OR comment LIKE ? OR name LIKE ? OR route LIKE ? OR  description LIKE ?";
        String queryPictures = "SELECT pictureURL FROM goodpicture WHERE goodId=?";
        try (PreparedStatement pstmtGood = conn.prepareStatement(queryGood);
             PreparedStatement pstmtPictures = conn.prepareStatement(queryPictures)) {
            pstmtGood.setString(1, "%" + city + "%");
            pstmtGood.setString(2, "%" + city + "%");
            pstmtGood.setString(3, "%" + city + "%");
            pstmtGood.setString(4, "%" + city + "%");
            pstmtGood.setString(5, "%" + city + "%");
            try (ResultSet rsetGood = pstmtGood.executeQuery()) {
                List<Good> goods = new ArrayList<>();
                while (rsetGood.next()) {
                    Good good = new Good();
                    String goodId = rsetGood.getString(1);
                    good.setId(goodId);
                    pstmtPictures.setString(1, goodId);

                    String str = URLEncoder.encode(rsetGood.getString(2), "UTF-8");
                    good.setName(str);
                    good.setPrice(rsetGood.getDouble(3));
                    str = URLEncoder.encode(rsetGood.getString(4), "UTF-8");
                    good.setRoute(str);

                    try (ResultSet rsetPictures = pstmtPictures.executeQuery()) {
                        List<String> picture = new ArrayList<>();
                        if (rsetPictures.next()) {
                            picture.add(rsetPictures.getString("pictureURL"));
                        }
                        good.setPictures(picture);
                    }
                    goods.add(good);
                }
                findStatus.setStatus("success");
                findStatus.setData(goods);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return findStatus;
    }

    @Override
    public Status findAll() throws SQLException {
        // 初始化为查询失败
        Status status = new Status();
        status.setContent("goodNotExist", "");
        status.setData(null);

        // 查询goodId
        String queryId = "SELECT Id FROM good";
        try (PreparedStatement pstmtId = conn.prepareStatement(queryId)) {
            try (ResultSet rsetId = pstmtId.executeQuery()) {
                List<Good> goods = new ArrayList<>();
                while (rsetId.next()) {
                    String goodId = rsetId.getString("Id");
                    Good good = (Good) findById(goodId).getData();
                    goods.add(good);
                }

                // 添加查询结果到状态
                status.setContent("success", "");     // 更改状态码
                status.setData(goods);
            }
        }
        return status;
    }

    @Override
    public Status publishGood(Good good) throws SQLException {
        Status status = new Status();

        String publishQuery = "INSERT INTO travelgo.good(Id,name, price, city, route, description,seller) VALUES (?,?,?,?,?,?,?)";
        String picQuery = "INSERT INTO travelgo.goodpicture(goodId, pictureURL) VALUES (?,?)";
        try (PreparedStatement pst = conn.prepareStatement(publishQuery)) {
            pst.setString(1, good.getId());
            pst.setString(2, good.getName());
            pst.setDouble(3, good.getPrice());
            pst.setString(4, good.getCity());
            pst.setString(5, good.getRoute());
            pst.setString(6, good.getDescription());
            pst.setString(7, good.getSeller());
            if (pst.executeUpdate() > 0) {
                String goodId = good.getId();
                List<String> picUrls = good.getPictures();
                try (PreparedStatement picPst = conn.prepareStatement(picQuery)) {
                    for (String url : picUrls) {
                        picPst.setString(1, goodId);
                        picPst.setString(2, url);
                        picPst.addBatch();
                    }
                    int[] result = picPst.executeBatch();
                    int resultSum = 0;
                    ArrayList<String> errorResult = new ArrayList<>();
                    for (int i = 0; i < result.length; i++) {
                        if (result[i] > 0) {
                            resultSum++;
                        } else {
                            errorResult.add(picUrls.get(i));
                            System.out.println(picUrls.get(i) + "添加错误");
                        }
                    }
                    if (resultSum == result.length) {
                        status.setContent("success", "Good Added successfully!");
                        status.setData(good);
                    } else {
                        status.setContent("fail", "");
                        status.setData(errorResult);
                    }
                }
            }
        }
        return status;
    }
}
