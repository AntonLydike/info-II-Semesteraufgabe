/*
 *
 * @Datenbank.java 02 03.06.2018 (Robert Lorenz)
 *
 * Copyright (c) 2017 Lehrprofessur für Informatik, Universität Augsburg
 *
 */

package data.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Establishes connection to the database, Singelton object
 *
 */
public class DataAccess {

    private static final String driver = "com.mysql.cj.jdbc.Driver";

    private static final String url = "jdbc:mysql://localhost:3306/filmdatenbank?useSSL=false";
    private static final String user = "admin";
    private static final String password = "admin";
    private Connection con = null;
    private static DataAccess unique = null;

    /**
     * Create connection to Database
     *
     */
    private DataAccess() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        con = DriverManager.getConnection(url, user, password);
    }

    /**
     * Access to singelton instance
     * @return dataAccess object for connecting to DB
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
            System.out.println("Datenbankfehler beim Schließen");
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
