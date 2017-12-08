package cn.corechan.travel.dbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The interface of connection & close of Database.
 */
public interface DatabaseConnection {
    /**
     * Gets a <code>Connection</code> (session) of a specific Database.
     * @return the connection of any Database
     */
    Connection getConnection();

    /**
     * Releases this <code>Connection</code> object's database and JDBC resources
     * immediately instead of waiting for them to be automatically released.
     * <P>
     * Calling the method <code>close</code> on a <code>Connection</code>
     * object that is already closed is a no-op.
     * <P>
     * It is <b>strongly recommended</b> that an application explicitly
     * commits or rolls back an active transaction prior to calling the
     * <code>close</code> method.  If the <code>close</code> method is called
     * and there is an active transaction, the results are implementation-defined.
     * <P>
     *
     * @exception SQLException SQLException if a database access error occurs
     */
    void close() throws SQLException;
}
