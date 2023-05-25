package com.example.Project2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;

public class chatController {
    @FXML
    private Button closeButton;
    @FXML
    private TextField inputTekst;
    @FXML
    private Label outputTekst;
    @FXML
    private Button sendButton;

    public void SendButtonOnAction(ActionEvent event){
        String input = inputTekst.getText();
        String onderwerp = vindOnderwerp(input);
        StringBuilder tableCheck = new StringBuilder();
        StringBuilder antwoord = new StringBuilder();
        String query = onderwerp;

        DatabaseConnection connection = new DatabaseConnection();
        try (Connection connectDB = connection.getConnection2()){
            PreparedStatement tableNames = connectDB.prepareStatement("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = " + query + "WHERE TABLE_SCHEMA = 'documentatie'");
            PreparedStatement statement = connectDB.prepareStatement("SELECT * FROM " + query);

            try (ResultSet queryResult2 = tableNames.executeQuery()){
                while (queryResult2.next()){
                    tableCheck.append(queryResult2.getString(1));
                    System.out.println(tableCheck);
                }
            }
            try (ResultSet queryResult = statement.executeQuery()) {
                while (queryResult.next()){
                    antwoord.append(queryResult.getString("kosten"));
                    antwoord.append(" ");
                }
                outputTekst.setText(antwoord.toString());
                System.out.println("test");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(onderwerp == null){
            outputTekst.setText("Hier heb ik geen informatie over.");
        }
        //else{outputTekst.setText("Uw gekozen onderwerp is " + onderwerp + ".");}
    }
    public String vindOnderwerp(String input){
        String[] tabellen = {"inkomen", "kosten", "medewerkers"};
        String[] apart = input.split(" ");
        for (String woord : apart){
            for (String tabel : tabellen){
                if (woord.toLowerCase().equals(tabel)){
                    return woord;
                }
            }
        }
        return null;
    }

    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}