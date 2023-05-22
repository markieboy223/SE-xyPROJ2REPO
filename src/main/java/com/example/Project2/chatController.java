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
import java.util.ArrayList;

public class chatController {
    @FXML
    private Button closeButton;
    @FXML
    private TextField inputTekst;
    @FXML
    private Label outputTekst;
    @FXML
    private Button sendButton;
    private ArrayList<String> tabellen = new ArrayList<>();

    public void SendButtonOnAction(ActionEvent event){
        DatabaseConnection connection = new DatabaseConnection();
        String input = inputTekst.getText();
        String onderwerp = vindOnderwerp(input);
        StringBuilder antwoord = new StringBuilder();

        if (onderwerp != null){
            try (Connection connectDB = connection.getConnection2()){
                PreparedStatement statement = connectDB.prepareStatement("SELECT * FROM " + onderwerp);

                try (ResultSet queryResult = statement.executeQuery()) {
                    while (queryResult.next()){
                        antwoord.append(queryResult.getString(onderwerp));
                        antwoord.append(" ");
                    }
                    outputTekst.setText("Q: " + input + "\n" + "A: " + antwoord.toString() + "\n");
                    inputTekst.clear();
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(onderwerp == null){
            String help = "Hier heb ik geen informatie over. \nIk heb alleen kennis over de volgende onderwerpen: \n";
            String help2 = "";
            for (String dit : tabellen){
                help2 =  help2 + dit + "\n";
            }
            help = help + help2;
            outputTekst.setText(help);
        }

    }
    public String vindOnderwerp(String input){
        DatabaseConnection connection = new DatabaseConnection();
        StringBuilder tableCheck = new StringBuilder();
        String [] apart = input.split(" ");

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