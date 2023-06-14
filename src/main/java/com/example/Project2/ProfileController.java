package com.example.Project2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileController {
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
    private String userName;

    @FXML
    public void initialize() {
        getUserInfo(userName);
        passwordField.setVisible(false);
        updatePasBtn.setVisible(false);
    }

    public void setUser(String userName) {
        this.userName = userName;
    }

    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void passChangeOnAction(ActionEvent event) {
        pasChangeBtn.setVisible(false);
        passwordField.setVisible(true);
        updatePasBtn.setVisible(true);
    }

    public void updatePassword(ActionEvent event) {
        String newPassword = passwordField.getText();
        if (!newPassword.isEmpty()) {
            DatabaseConnection connection = new DatabaseConnection();
            try (Connection connectDB = connection.getConnectionGebruiker();
                 PreparedStatement statement = connectDB.prepareStatement("UPDATE docassistent.user SET wachtwoord = ? WHERE gebruikersnaam = ?")) {
                statement.setString(1, newPassword);
                statement.setString(2, userName);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please enter a new password.");
        }
    }

    public void getUserInfo(String userName) {
        DatabaseConnection connection = new DatabaseConnection();
        try (Connection connectDB = connection.getConnectionGebruiker();
             PreparedStatement statement = connectDB.prepareStatement("SELECT gebruikersnaam, email, voornaam, achternaam, rol FROM docassistent.user WHERE gebruikersnaam = ?")) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
