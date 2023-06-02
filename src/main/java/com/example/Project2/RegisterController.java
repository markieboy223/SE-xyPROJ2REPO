package com.example.Project2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Statement;

public class RegisterController {
    @FXML
    private Button closeButton;
    @FXML
    private TextField gebruikersnaamTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField confirmPasswordTextField;
    @FXML
    private ComboBox<String> rolBox;
    @FXML
    private Button registerButton;
    @FXML
    private Label messageLabel;

    public void initialize() {
        // Add language options to the ComboBox
        rolBox.getItems().addAll("admin", "manager", "medewerker");
        // Set the default selected language
        rolBox.getSelectionModel().selectFirst();
    }

    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void registerButtonOnAction() {
        if (passwordTextField.getText().equals(confirmPasswordTextField.getText()) && !gebruikersnaamTextField.getText().isBlank()){
            registerUser();
        } else if (gebruikersnaamTextField.getText().isBlank()) {
            messageLabel.setText("Gebruikersnaam niet ingevuld");
            messageLabel.setTextFill(Color.RED);
            CornerRadii corn = new CornerRadii(4);
            messageLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, corn, Insets.EMPTY)));
        } else {
            messageLabel.setText("Wachtwoord komt niet overeen");
            messageLabel.setTextFill(Color.RED);
            CornerRadii corn = new CornerRadii(4);
            messageLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, corn, Insets.EMPTY)));
        }
    }
    public void registerUser() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnectionGebruiker();
        String username = gebruikersnaamTextField.getText();
        String password = passwordTextField.getText();
        String role = rolBox.getValue();

        String insertFields = "INSERT INTO user(gebruikersnaam, wachtwoord, rol) VALUES ('";
        String insertValues = username + "','" + password + "','" + role + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

}
