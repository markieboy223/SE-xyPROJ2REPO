package com.example.Project2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class chatController {
    @FXML
    private Button closeButton;
    @FXML
    private TextField inputTekst;
    @FXML
    private Button sendButton;

    public void SendButtonOnAction(ActionEvent event){
        String input = inputTekst.getText();
        DatabaseConnection connection = new DatabaseConnection();
        try (Connection connectDB = connection.getConnection()){

        }
         catch (SQLException e) {
            throw new RuntimeException(e);
        }
}

    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}