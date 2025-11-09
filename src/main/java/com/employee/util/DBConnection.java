package com.employee.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    // AWS RDS Configuration
    private static final String DB_URL = "jdbc:mysql://database-1.cdwkuiksmrsm.ap-south-1.rds.amazonaws.com:3306/employeedb";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "YourPassword123";
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("✅ Connected to AWS RDS successfully!");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to AWS RDS!");
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
