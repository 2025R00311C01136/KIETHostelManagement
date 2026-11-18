package com.kiet.hostel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/hostel_db";

    private static final String USER = "root";

    private static final String PASSWORD = System.getenv("DB_PASSWORD");


    public static Connection getConnection() {

        Connection con = null;

        try {

            // Load MySQL JDBC Driver

            Class.forName("com.mysql.jdbc.Driver");


            // Establish Connection

            con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("MySQL Connected Successfully!");

        } catch (ClassNotFoundException e) {

            System.out.println("JDBC Driver Not Found!");

            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println("Database Connection Failed!");

            e.printStackTrace();
        }

        return con;
    }
}