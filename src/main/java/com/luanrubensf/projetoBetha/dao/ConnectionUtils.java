package com.luanrubensf.projetoBetha.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rubens
 */
public class ConnectionUtils {
    
    private static final String url = "jdbc:postgresql://localhost:5432/final-project";
    private static final String user = "postgres";
    private static final String pass = "postgres123";
    
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static final Connection getConn() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}