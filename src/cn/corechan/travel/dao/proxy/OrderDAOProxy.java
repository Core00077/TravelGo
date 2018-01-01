package cn.corechan.travel.dao.proxy;

import cn.corechan.travel.dao.IOrderDAO;
import cn.corechan.travel.dao.impl.OrderDAOImpl;
import cn.corechan.travel.dbc.DatabaseConnection;
import cn.corechan.travel.factory.DatabaseConnectionFactor;
import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.Order;

import java.sql.SQLException;

public class OrderDAOProxy implements IOrderDAO {
    private DatabaseConnection dbc;
    private IOrderDAO orderDAO;

    public OrderDAOProxy() throws SQLException, ClassNotFoundException {
        this.dbc = DatabaseConnectionFactor.getMySQLDatabaseConnection();
        this.orderDAO = new OrderDAOImpl(dbc.getConnection());
    }

    @Override
    public Status CreateOrder(Order order) throws SQLException {
        try {
            return orderDAO.CreateOrder(order);
        } finally {
            dbc.close();
        }
    }

    @Override
    public Status FindOrders(String phoneNumber, int s) throws SQLException {
        try {
            return orderDAO.FindOrders(phoneNumber, s);
        } finally {
            dbc.close();
        }
    }

    @Override
    public Status FindSellerOrders(String phoneNumber, int s) throws SQLException {
        try {
            return orderDAO.FindSellerOrders(phoneNumber, s);
        } finally {
            dbc.close();
        }
    }

    @Override
    public Status CommentOrder(String orderId) throws SQLException {
        try {
            return orderDAO.CommentOrder(orderId);
        } finally {
            dbc.close();
        }
    }

    @Override
    public Status PayOrder(String orderId) throws SQLException {
        try {
            return orderDAO.PayOrder(orderId);
        } finally {
            dbc.close();
        }
    }

    @Override
    public Status CancelOrderCustomer(String orderId) throws SQLException {
        try {
            return orderDAO.CancelOrderCustomer(orderId);
        } finally {
            dbc.close();
        }
    }

    @Override
    public Status CancelOrderSeller(String orderId) throws SQLException {
        try {
            return orderDAO.CancelOrderSeller(orderId);
        } finally {
            dbc.close();
        }
    }

    @Override
    public Order FindOrder(String orderId) throws SQLException {
        try {
            return orderDAO.FindOrder(orderId);
        } finally {
            dbc.close();
        }
    }

    @Override
    public Order FindOrder(String orderId, int s) throws SQLException {
        try {
            return orderDAO.FindOrder(orderId, s);
        } finally {
            dbc.close();
        }
    }

    @Override
    public boolean ChangeOrderStatus(String orderId, int s) throws SQLException {
        try {
            return orderDAO.ChangeOrderStatus(orderId, s);
        } finally {
            dbc.close();
        }
    }
}
