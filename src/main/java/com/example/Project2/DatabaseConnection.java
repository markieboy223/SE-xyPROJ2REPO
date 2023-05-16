package com.example.Project2;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseGebruiker;
    public Connection databaseDocumentatie;
    public Connection getConnection(){
        String databaseName = "docassistent";
        String databaseUser = "root";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseGebruiker = DriverManager.getConnection(url, databaseUser, databasePassword);
        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return databaseGebruiker;

    }

    public Connection getConnection2(){
        String databaseName = "documentatie";
        String databaseUser = "root";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseDocumentatie = DriverManager.getConnection(url, databaseUser, databasePassword);
        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return databaseDocumentatie;

    }
}
