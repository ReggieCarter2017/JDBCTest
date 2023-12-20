package org.jdbcproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    public static Connection connect() {
        Connection con = null;
        final String URL = "jdbc:mysql://localhost:3306/somename";
        final String NAME = "root";
        final String PASSWORD = "password";
        try {
            con = DriverManager.getConnection(URL, NAME, PASSWORD);
            return con;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database: " + e.getMessage());
        }
        return null;
    }


}
