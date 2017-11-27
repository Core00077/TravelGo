package cn.corechan.travel.dbc.impl;

import cn.corechan.travel.dbc.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabaseConnection implements DatabaseConnection{
    private static final String DBDRIVER = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/travelgo";
    private static final String DBUSER = "root";
    private static final String DBPASSWORD = "19961107";
    private Connection conn = null;

    public MySQLDatabaseConnection() throws ClassNotFoundException, SQLException {
        try {
            Class.forName(DBDRIVER);
            this.conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        }
    }

    /**
     * Gets a <code>Connection</code> (session) of a specific Database.
     *
     * @return the connection of any Database
     */
    @Override
    public Connection getConnection() {
        return conn;
    }

    /**
     * Releases this <code>Connection</code> object's database and JDBC resources
     * immediately instead of waiting for them to be automatically released.
     * <p>
     * Calling the method <code>close</code> on a <code>Connection</code>
     * object that is already closed is a no-op.
     * <p>
     * It is <b>strongly recommended</b> that an application explicitly
     * commits or rolls back an active transaction prior to calling the
     * <code>close</code> method.  If the <code>close</code> method is called
     * and there is an active transaction, the results are implementation-defined.
     * <p>
     *
     * @throws SQLException SQLException if a database access error occurs
     */
    @Override
    public void close() throws SQLException {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException e) {
                throw e;
            }
        }
    }
}
