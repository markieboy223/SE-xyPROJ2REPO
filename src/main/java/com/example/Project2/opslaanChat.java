package com.example.Project2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class opslaanChat {
    private String vraag;
    private String antwoord;
    private String onderwerp;
    public int userID;
    public void opslaan(String vraag, String antwoord, String onderwerp, int userID) {
        this.vraag = vraag;
        this.antwoord = antwoord;
        this.onderwerp = onderwerp;
        this.userID = userID;
        slaOp();
    }
    public void slaOp(){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnectionGebruiker();
        String insertFields = "INSERT INTO chatgeschiedenis(onderwerp, vraag, antwoord, user_id) VALUES ('";
        String insertValues = onderwerp + "','" + vraag + "','" + antwoord + "','" + userID + "')";
        String insertToRegister = insertFields + insertValues;
        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
