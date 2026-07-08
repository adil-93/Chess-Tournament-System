package com.chess.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/chess_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root"; 
    private static final String PASSWORD = "Adil@9392"; // <-- Replace with your real MySQL root password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}