/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kel4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author ANISETUS B. MANALU
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost/bookingfutsal";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "anisetus";

     public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}