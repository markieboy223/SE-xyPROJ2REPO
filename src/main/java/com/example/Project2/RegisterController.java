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
        String username = gebruikersnaamTextField.getText();
        String password = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        if (username.isBlank()) {
            setErrorMessage("Gebruikersnaam niet ingevuld");
        } else if (password.isBlank()) {
            setErrorMessage("Wachtwoord niet ingevuld");
        } else if (!password.equals(confirmPassword)) {
            setErrorMessage("Wachtwoord komt niet overeen");
        } else {
            registerUser();
        }
    }
    private void setErrorMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setTextFill(Color.RED);
        CornerRadii corn = new CornerRadii(4);
        messageLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, corn, Insets.EMPTY)));
    }
    public void registerUser() {
        String username = gebruikersnaamTextField.getText();
        String email = emailTextField.getText();
        String voornaam = voornaamTextField.getText();
        String achternaam = achternaamTextField.getText();
        String telefoonnummer = telefoonTextField.getText();
        String password = passwordTextField.getText();
        String role = rolBox.getValue();
        int layout = 0;

        try (Connection connectDB = getConnection()) {
            insertUser(connectDB, username, email, voornaam, achternaam, telefoonnummer, password, role, layout);
            messageLabel.setText("Gebruiker Toegevoegd");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Connection getConnection() {
        DatabaseConnection connection = new DatabaseConnection();
        return connection.getConnectionGebruiker();
    }
    private void insertUser(Connection connectDB, String username, String email, String voornaam, String achternaam,
                            String telefoonnummer, String password, String role, int layout) throws SQLException {
        String insertFields = "INSERT INTO user(gebruikersnaam, email, voornaam, achternaam, telefoonnummer, wachtwoord, rol, layout) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connectDB.prepareStatement(insertFields)) {
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, voornaam);
            statement.setString(4, achternaam);
            statement.setString(5, telefoonnummer);
            statement.setString(6, password);
            statement.setString(7, role);
            statement.setInt(8, layout);
            statement.executeUpdate();
        }
    }
}
