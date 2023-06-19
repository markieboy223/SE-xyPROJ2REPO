package com.example.Project2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileController {
    @FXML
    private Label passwordMessage;
    @FXML
    private Button closeButton;
    @FXML
    private Button pasChangeBtn;
    @FXML
    private Button updatePasBtn;
    @FXML
    private Label emailLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private TextField passwordField;
    private User user;

    @FXML
    public void initialize() {
        if (user != null) {
            getUserInfo(user.getUsername());
            passwordField.setVisible(false);
            updatePasBtn.setVisible(false);
        }
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void closeButtonOnAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    public void passChangeOnAction() {
        pasChangeBtn.setVisible(false);
        passwordField.setVisible(true);
        updatePasBtn.setVisible(true);
    }
    public void updatePassword() {
        String newPassword = passwordField.getText();
        if (!newPassword.isEmpty()) {
            updatePasswordInDatabase(newPassword);
        } else {
            handleEmptyPassword();
        }
    }
    private void updatePasswordInDatabase(String newPassword) {
        DatabaseConnection connection = new DatabaseConnection();
        try (Connection connectDB = connection.getConnectionGebruiker();
             PreparedStatement statement = connectDB.prepareStatement("UPDATE docassistent.user SET wachtwoord = ? WHERE gebruikersnaam = ?")) {
            statement.setString(1, newPassword);
            statement.setString(2, user.getUsername());
            statement.executeUpdate();
            pasChangeBtn.setVisible(true);
            passwordField.setVisible(false);
            updatePasBtn.setVisible(false);
            passwordMessage.setText("Wachtwoord gewijzigd naar: " + newPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void handleEmptyPassword() {
        passwordMessage.setText("Geen wachtwoord ingevuld om te wijzigen");
    }
    public void getUserInfo(String userName) {
        DatabaseConnection connection = new DatabaseConnection();
        try (Connection connectDB = connection.getConnectionGebruiker();
             PreparedStatement statement = connectDB.prepareStatement("SELECT gebruikersnaam, email, voornaam, achternaam, rol FROM docassistent.user WHERE gebruikersnaam = ?")) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                displayUserInfo(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void displayUserInfo(ResultSet resultSet) throws SQLException {
        String gebruikersnaam = resultSet.getString("gebruikersnaam");
        String email = resultSet.getString("email");
        String voornaam = resultSet.getString("voornaam");
        String achternaam = resultSet.getString("achternaam");
        String rol = resultSet.getString("rol");

        usernameLabel.setText(gebruikersnaam);
        emailLabel.setText(email);
        nameLabel.setText(voornaam);
        surnameLabel.setText(achternaam);
        roleLabel.setText(rol);
    }
}
