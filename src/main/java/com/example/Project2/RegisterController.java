package com.example.Project2;

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

    public void closeButtonOnAction() {
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

        User user = new User(0 ,username, email, voornaam, achternaam, telefoonnummer, password, role);

        try (Connection connectDB = getConnection()) {
            insertUser(connectDB, user);
            messageLabel.setText("Gebruiker Toegevoegd");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        DatabaseConnection connection = new DatabaseConnection();
        return connection.getConnectionGebruiker();
    }

    private void insertUser(Connection connectDB, User user) throws SQLException {
        String insertFields = "INSERT INTO user(gebruikersnaam, email, voornaam, achternaam, telefoonnummer, wachtwoord, rol, layout) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connectDB.prepareStatement(insertFields)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getVoornaam());
            statement.setString(4, user.getAchternaam());
            statement.setString(5, user.getTelefoonnummer());
            statement.setString(6, user.getPassword());
            statement.setString(7, user.getRole());
            statement.setInt(8, 0);
            statement.executeUpdate();
        }
    }
}
