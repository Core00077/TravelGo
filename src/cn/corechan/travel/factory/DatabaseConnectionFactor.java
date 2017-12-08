package cn.corechan.travel.factory;

import cn.corechan.travel.dbc.DatabaseConnection;
import cn.corechan.travel.dbc.impl.MySQLDatabaseConnection;

import java.sql.SQLException;

public class DatabaseConnectionFactor {
    public static DatabaseConnection getMySQLDatabaseConnection()
            throws ClassNotFoundException, SQLException {
        return new MySQLDatabaseConnection();
    }
}
