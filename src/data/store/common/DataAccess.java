/*
 *
 * @Datenbank.java 02 03.06.2018 (Robert Lorenz)
 *
 * Copyright (c) 2017 Lehrprofessur für Informatik, Universität Augsburg
 *
 */

package data.store.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/** Establishes connection to the database, Singelton object
 *
 */
public class DataAccess {

    private static final String driver = "com.mysql.cj.jdbc.Driver";

    private static final String url = "jdbc:mysql://s62.goserver.host:3306/web223_db9?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // not sure if useSSL=false is needed
    private static final String user = "web223_9";
    private static final String password = "AavNRk6mgGBur2dR+WimITGkRcGYPOc+";
    // db name: web223_db9
    private Connection con = null;
    private static DataAccess unique = null;

    private static final Logger LOGGER = Logger.getLogger(DataAccess.class.getName());

    /**
     * Create connection to Database
     * @throws ClassNotFoundException thrown if DataAccess could not be initialized
     * @throws SQLException thrown if connection to database is not possible
     */
    private DataAccess() throws ClassNotFoundException, SQLException {
        LOGGER.info("Open connection to database");
        Class.forName(driver);
        con = DriverManager.getConnection(url, user, password);
    }

    /**
     * Access to singelton instance
     * @return dataAccess object for connecting to DB
     * @throws ClassNotFoundException thrown if DataAccess could not be initialized
     */
    public static DataAccess instance() throws ClassNotFoundException,
            SQLException {
        if (unique == null)
            unique = new DataAccess();
        return unique;
    }

    /**
     * Close the connection
     */
    public void close() {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            LOGGER.severe("Failed to close connection: " + e.getMessage());
        }
    }

    /**
     * get the connection to execute statements etc.
     * @return the active connection
     */
    public Connection getConnection() {
        return this.con;
    }
}
