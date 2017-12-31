package cn.corechan.travel.dao;

import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.Order;

import java.sql.SQLException;

public interface IOrderDAO {
    Status CreateOrder(Order order) throws SQLException;

    Status FindOrders(String phoneNumber) throws SQLException;

    Status FindSellerOrders(String phoneNumber)throws SQLException;

    boolean ChangeOrderStatus(String orderId,int s) throws SQLException;

    Status CommentOrder(String orderId) throws SQLException;

    Status CancelOrderCustomer(String orderId) throws SQLException;

    Status CancelOrderSeller(String orderId) throws SQLException;

    Order FindOrder(String orderId) throws SQLException;

    Status PayOrder(String orderId) throws SQLException;
}
