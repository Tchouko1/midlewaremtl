package com.midleware.utils;

import java.sql.*;

public class MidleWareSQL {

    static Connection connection = null;
    static PreparedStatement preparedStatement = null;



    public static void makeJDBCConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            log("Congrats - Seems your MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            log("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
            e.printStackTrace();
            return;
        }

        try {
            // DriverManager: The basic service for managing a set of JDBC drivers.
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MTLDB", "root", "root");
            if (connection != null) {
                log("Connection Successful! Enjoy.");
            } else {
                log("Failed to make connection!");
            }
        } catch (SQLException e) {
            log("MySQL Connection Failed!");
            e.printStackTrace();
            return;
        }

    }


    public static void addDataToDB(String companyName, String address, int totalEmployee, String webSite) {

        try {
            String insertQueryStatement = "INSERT  INTO  Employee  VALUES  (?,?,?,?)";

            preparedStatement = connection.prepareStatement(insertQueryStatement);
            preparedStatement.setString(1, companyName);
            preparedStatement.setString(2, address);
            preparedStatement.setInt(3, totalEmployee);
            preparedStatement.setString(4, webSite);

            // execute insert SQL statement
            preparedStatement.executeUpdate();
            log(companyName + " added successfully");
        } catch (

                SQLException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection() {
        return connection;
    }


    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }



    public static String getStationIDFromDB(String arrondissementVille) {

        try {
            // MySQL Select Query Tutorial
            String getQueryStatement = "SELECT * FROM Address_Station WHERE ArrondissementVille = ?";

            preparedStatement = connection.prepareStatement(getQueryStatement);
            preparedStatement.setString(1, arrondissementVille);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = preparedStatement.executeQuery();

            // Let's iterate through the java ResultSet
            while (rs.next()) {

                String numeroStation = rs.getString("Numro_station");
                // Simply Print the results
                System.out.format("%s\n", numeroStation);
                return numeroStation;
            }

        } catch (

                SQLException e) {
            e.printStackTrace();
        }

        return null;

    }




    // Simple log utility
    public static void log(String string) {
        System.out.println(string);

    }

}
