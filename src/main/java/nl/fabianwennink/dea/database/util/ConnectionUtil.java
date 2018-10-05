package nl.fabianwennink.dea.database.util;

import java.sql.*;

public class ConnectionUtil {

    /**
     * Close the SQL connection.
     *
     * @param conn	The connection to the database
     * @param ps	The statement
     * @param res	The fetched result
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
        if (res != null) try { res.close(); } catch (SQLException ignored) {}
    }

    /**
     * Close the SQL connection.
     *
     * @param conn	The connection to the database
     * @param ps	The statement
     * @param res	The fetched result
     */
    public static void close(Connection conn, Statement ps, ResultSet res) {
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
        if (res != null) try { res.close(); } catch (SQLException ignored) {}
    }
}
