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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterController {
    @FXML
    private Button closeButton;
    @FXML
    private TextField gebruikersnaamTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField voornaamTextField;
    @FXML
    private TextField achternaamTextField;
    @FXML
    private TextField telefoonTextField;
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
        String email = emailTextField.getText();
        String voornaam = voornaamTextField.getText();
        String achternaam = achternaamTextField.getText();
        String telefoonnummer = telefoonTextField.getText();
        String password = passwordTextField.getText();
        String role = rolBox.getValue();
        int layout = 0;

        String insertFields = "INSERT INTO user(gebruikersnaam, email, voornaam, achternaam, telefoonnummer, wachtwoord, rol, layout) VALUES (?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement statement = connectDB.prepareStatement(insertFields);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, voornaam);
            statement.setString(4, achternaam);
            statement.setString(5, telefoonnummer);
            statement.setString(6, password);
            statement.setString(7, role);
            statement.setInt(8, layout);
            statement.executeUpdate();
            messageLabel.setText("Gebruiker Toegevoegd");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
