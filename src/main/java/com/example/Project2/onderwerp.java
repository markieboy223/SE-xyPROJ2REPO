package com.example.Project2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class onderwerp {
    protected ArrayList<String> tabellen = new ArrayList<>();
    public void maakOnderwerpen(){
        DatabaseConnection connection = new DatabaseConnection();
        StringBuilder tableCheck = new StringBuilder();

        try (Connection connectDB = connection.getConnection2()){
            PreparedStatement tableNames = connectDB.prepareStatement("SELECT DISTINCT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'documentatie'");
            try (ResultSet queryResult2 = tableNames.executeQuery()){
                while (queryResult2.next()){
                    tableCheck.append(queryResult2.getString(1));
                    tabellen.add(String.valueOf(tableCheck));
                    tableCheck.setLength(0);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
