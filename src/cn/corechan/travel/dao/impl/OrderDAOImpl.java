package cn.corechan.travel.dao.impl;

import cn.corechan.travel.dao.IOrderDAO;
import cn.corechan.travel.util.json.Status;
import cn.corechan.travel.vo.Contact;
import cn.corechan.travel.vo.Order;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDAOImpl implements IOrderDAO {
    private Connection conn;

    private String getViewTable(int s) {
        String table = "order_";
        switch (s) {
            case 0:
                table += "unpay";
                break;
            case 1:
                table += "ungo";
                break;
            case 2:
                table += "uncomment";
                break;
            case 3:
                table += "finished";
                break;
        }
        return table;
    }

    public OrderDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Status CreateOrder(Order order) throws SQLException {
        String OrderInsert = "INSERT INTO `order`(orderId, goodId, phonenumber, ordertime, gotime, people, note) VALUES (?,?,?,?,?,?,?)";
        String OrderContactsInsert = "INSERT INTO order_contacts(orderId, name, phonenumber) VALUES (?,?,?)";
        Status status = new Status();
        try (PreparedStatement preparedStatement = conn.prepareStatement(OrderInsert)) {
            preparedStatement.setString(1, order.getOrderId());
            preparedStatement.setString(2, order.getGoodId());
            preparedStatement.setString(3, order.getPhoneNumber());
            preparedStatement.setString(4, order.getOrdertime());
            preparedStatement.setString(5, order.getGotime());
            preparedStatement.setInt(6, order.getPeople());
            preparedStatement.setString(7, order.getNote());
            if (preparedStatement.executeUpdate() > 0) {
                try (PreparedStatement statement = conn.prepareStatement(OrderContactsInsert)) {
                    for (Contact contact : order.getContacts()) {
                        statement.setString(1, order.getOrderId());
                        statement.setString(2, contact.getName());
                        statement.setString(3, contact.getPhoneNumber());
                        statement.addBatch();
                    }
                    int[] result = statement.executeBatch();
                    int resultSum = 0;
                    List<Contact> error = new ArrayList<>();
                    for (int i = 0; i < result.length; i++) {
                        if (result[i] > 0)
                            resultSum++;
                        else
                            error.add(order.getContacts().get(i));
                    }
                    if (resultSum == result.length) {
                        status.setContent("success", "Order added successfully!");
                    } else {
                        status.setContent("contactInsertError", "Contacts insert error");
                        status.setData(error);
                    }
                }
            } else {
                status.setContent("GoodNotExist", "商品不存在");
            }
        }
        return status;
    }

    @Override
    public Status FindSellerOrders(String phoneNumber, int s) throws SQLException {
        String table = getViewTable(s);
        String sellerOrdersSQL = "SELECT orderId FROM "+table+" JOIN good ON "+table+".goodId = good.Id WHERE seller=? ORDER BY ordertime DESC ";
        Status status = new Status();
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(sellerOrdersSQL)) {
            preparedStatement.setString(1, phoneNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(FindOrder(resultSet.getString(1),s));
                }
                status.setContent("success", "Find Seller Orders successfully!");
                status.setData(orders);
            }
        }
        return status;
    }

    @Override
    public Status FindOrders(String phoneNumber, int s) throws SQLException {
        String table = getViewTable(s);
        Status status = new Status();
        String orderIdsSQL = "SELECT orderId FROM " + table + " WHERE phonenumber=? ORDER BY ordertime DESC";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(orderIdsSQL)) {
            preparedStatement.setString(1, phoneNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(FindOrder(resultSet.getString("orderId"), s));
                }
            }
            status.setData(orders);
            status.setContent("success", "Orders found successfully!");
        }
        return status;
    }

    @Override
    public Status CommentOrder(String orderId) throws SQLException {
        Order order = FindOrder(orderId);
        Status status = new Status();
        if (order == null) {
            status.setContent("OrderNotExist", "Order Not Exists");
            return status;
        }
        if (order.getStatus() == 2) {
            if (ChangeOrderStatus(orderId, 3)) {
                status.setContent("success", "Comment submitted successfully!");
            }
        } else {
            status.setContent("Invalid", "The order status is wrong!");
        }
        return status;
    }

    @Override
    public Status PayOrder(String orderId) throws SQLException {
        Order order = FindOrder(orderId);
        Status status = new Status();
        if (order == null) {
            status.setContent("OrderNotExist", "Order Not Exists");
            return status;
        }
        if (order.getStatus() == 0) {
            if (ChangeOrderStatus(orderId, 1)) {
                status.setContent("success", "Payed successfully!");
            }
        } else {
            status.setContent("Invalid", "The order status is wrong!");
        }
        return status;
    }

    @Override
    public Status CancelOrderCustomer(String orderId) throws SQLException {
        Order order = FindOrder(orderId);
        Status status = new Status();
        if (order == null) {
            status.setContent("OrderNotExist", "Order Not Exists");
            return status;
        }
        if (order.getStatus() == 2 || order.getStatus() == 3 || order.getStatus() == -1) {
            status.setContent("Invalid", "The order status is wrong!");
            return status;
        }
        boolean cancel = !order.isCustomCancel();
        String cancelSQL = "UPDATE `order` SET customCancel=? WHERE orderId=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(cancelSQL)) {
            preparedStatement.setBoolean(1, cancel);
            preparedStatement.setString(2, orderId);
            if (preparedStatement.executeUpdate() > 0)
                status.setContent("success", "update cancel status successfully!");
        }
        return status;
    }

    @Override
    public Status CancelOrderSeller(String orderId) throws SQLException {
        Order order = FindOrder(orderId);
        Status status = new Status();
        if (order == null) {
            status.setContent("OrderNotExist", "Order Not Exists");
            return status;
        }
        if (order.getStatus() == 2 || order.getStatus() == 3 || order.getStatus() == -1) {
            status.setContent("Invalid", "The order status is wrong!");
            return status;
        }
        boolean cancel = !order.isSellerCancel();
        String cancelSQL = "UPDATE `order` SET sellerCancel=? WHERE orderId=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(cancelSQL)) {
            preparedStatement.setBoolean(1, cancel);
            preparedStatement.setString(2, orderId);
            if (preparedStatement.executeUpdate() > 0)
                status.setContent("success", "update cancel status successfully!");
        }
        return status;
    }

    @Override
    public Order FindOrder(String orderId) throws SQLException {
        String orderSQL = "SELECT `order`.*,certificate.realname, certificate.contact,good.name,good.price " +
                "FROM `order` JOIN good JOIN certificate ON `order`.goodId = good.Id AND seller=certificate.phonenumber " +
                "WHERE orderId=?";
        return getOrder(orderId, orderSQL);
    }

    @Override
    public Order FindOrder(String orderId, int s) throws SQLException {
        String table = getViewTable(s);
        String orderSQL = "SELECT " + table + ".*,certificate.realname, certificate.contact,good.name,good.price " +
                "FROM " + table + " JOIN good JOIN certificate ON " + table + ".goodId = good.Id AND seller=certificate.phonenumber " +
                "WHERE orderId=?";
        return getOrder(orderId, orderSQL);
    }

    private Order getOrder(String orderId, String orderSQL) throws SQLException {
        Order order = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement(orderSQL)) {
            preparedStatement.setString(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String orderContactsSQL = "SELECT * FROM order_contacts WHERE orderId=?";
                    List<Contact> orderContacts = new ArrayList<>();
                    try (PreparedStatement statement = conn.prepareStatement(orderContactsSQL)) {
                        statement.setString(1, orderId);
                        try (ResultSet set = statement.executeQuery()) {
                            while (set.next()) {
                                orderContacts.add(new Contact(URLEncoder.encode(set.getString("name"), "UTF-8"), set.getString("phonenumber")));
                            }
                        }
                    }
                    String orderGoodPicSQL = "SELECT pictureURL FROM travelgo.goodpicture WHERE goodId=? LIMIT 1";
                    String picUrl = "";
                    try (PreparedStatement pst = conn.prepareStatement(orderGoodPicSQL)) {
                        pst.setString(1, resultSet.getString("goodId"));
                        try (ResultSet set = pst.executeQuery()) {
                            if (set.next())
                                picUrl = set.getString("pictureURL");
                        }
                    }
                    HashMap<String, String> seller = new HashMap<>();
                    seller.put("realname", URLEncoder.encode(resultSet.getString("certificate.realname"), "UTF-8"));
                    seller.put("phoneNumber", resultSet.getString("certificate.contact"));
                    HashMap<String, String> good = new HashMap<>();
                    good.put("name", URLEncoder.encode(resultSet.getString("good.name"), "UTF-8"));
                    good.put("price", resultSet.getString("good.price"));
                    good.put("picUrl", picUrl);
                    order = new Order(resultSet.getString("orderId"),
                            resultSet.getString("goodId"),
                            resultSet.getString("phoneNumber"),
                            resultSet.getString("ordertime"),
                            resultSet.getString("gotime"),
                            resultSet.getInt("people"),
                            URLEncoder.encode(resultSet.getString("note"), "UTF-8"),
                            resultSet.getInt("status"),
                            resultSet.getBoolean("customCancel"),
                            resultSet.getBoolean("sellerCancel"),
                            orderContacts, seller, good);
                    //检查订单是否已过出行时间，如果已过，订单若未付款，则取消订单，若已付款，则更新状态
                    if (order.getGotime().compareTo(String.valueOf(System.currentTimeMillis())) < 0) {
                        if (order.getStatus() == 0) {
                            order.setStatus(-1);
                            ChangeOrderStatus(order.getOrderId(), -1);
                        }
                        if (order.getStatus() == 1) {
                            order.setStatus(2);
                            ChangeOrderStatus(order.getOrderId(), 2);
                        }
                    }
                    //检查买家卖家是否均取消订单
                    if (order.isSellerCancel() && order.isCustomCancel()) {
                        order.setStatus(-1);
                        ChangeOrderStatus(order.getOrderId(), -1);
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return order;
    }

    @Override
    public boolean ChangeOrderStatus(String orderId, int s) throws SQLException {
        String updateStatusSQL = "UPDATE `order` SET status=? WHERE orderId=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateStatusSQL)) {
            preparedStatement.setInt(1, s);
            preparedStatement.setString(2, orderId);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }
}
