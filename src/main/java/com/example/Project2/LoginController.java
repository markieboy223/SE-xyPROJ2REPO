package com.example.Project2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class LoginController{
    @FXML
    private Button closeButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label LoginMessageLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField gebruikersnaamTextField;
    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private ComboBox<String> languageComboBox;
    private int check = -5;
    private String username = "";
    private int layout = 0;
    private String rol = "";
    public void initialize() {
        // Add language options to the ComboBox
        languageComboBox.getItems().addAll("Nederlands", "English");
        // Set the default selected language
        languageComboBox.getSelectionModel().selectFirst();
    }

    public void handleLanguageSelection(ActionEvent event) {
        String selectedLanguage = languageComboBox.getValue();
        if (selectedLanguage.equals("Nederlands")) {
            usernameLabel.setText("Gebruikersnaam");
            closeButton.setText("Afsluiten");
            passwordLabel.setText("Wachtwoord");
            loginButton.setText("Inloggen");

        } else if (selectedLanguage.equals("English")) {
            usernameLabel.setText("Username");
            closeButton.setText("Close");
            passwordLabel.setText("Password");
            loginButton.setText("Login");

        }
    }

    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    public void closeCurrentWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    public void loginButtonOnAction(ActionEvent event){

        if (gebruikersnaamTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false){
            validateLogin();
        } else {
            LoginMessageLabel.setText("Voer een gebruikersnaam en wachtwoord in.");
            LoginMessageLabel.setTextFill(Color.RED);
            CornerRadii corn = new CornerRadii(4);
            LoginMessageLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, corn, Insets.EMPTY)));
        }
    }
    public void redirectToNewScene() {
        String path = ChatViewPathResolver.resolvePath(layout);
        ControllerUtils.initializeChatView(path, layout, check, username, rol, languageComboBox.getValue());
    }
    public void validateLogin() {
        DatabaseConnection connection = new DatabaseConnection();
        try (Connection connectDB = connection.getConnectionGebruiker();
             PreparedStatement statement = connectDB.prepareStatement("SELECT COUNT(1), id, rol, layout FROM docassistent.user WHERE gebruikersnaam = ? AND wachtwoord = ?")) {
            statement.setString(1, gebruikersnaamTextField.getText());
            statement.setString(2, passwordTextField.getText());

            try (ResultSet queryResult = statement.executeQuery()) {
                if (queryResult.next() && queryResult.getInt(1) == 1) {
                    handleSuccessfulLogin(queryResult);
                } else {
                    handleFailedLogin();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleSuccessfulLogin(ResultSet queryResult) throws SQLException {
        check = queryResult.getInt("id");
        rol = queryResult.getString("rol");
        layout = queryResult.getInt("layout");
        username = gebruikersnaamTextField.getText();
        setLoginMessage("Ingelogd!", Color.GREEN);
        redirectToNewScene();
        closeCurrentWindow();
    }

    private void handleFailedLogin() {
        setLoginMessage("Ongeldige login, probeer het opnieuw!", Color.RED);
    }

    private void setLoginMessage(String message, Color color) {
        LoginMessageLabel.setText(message);
        LoginMessageLabel.setTextFill(color);
        CornerRadii corn = new CornerRadii(4);
        LoginMessageLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, corn, Insets.EMPTY)));
    }
}