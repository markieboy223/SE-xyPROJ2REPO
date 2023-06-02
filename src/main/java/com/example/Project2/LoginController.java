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
        } else if (selectedLanguage.equals("English")) {
            usernameLabel.setText("Username");
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
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chat-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void validateLogin() {
        DatabaseConnection connection = new DatabaseConnection();
        try (Connection connectDB = connection.getConnectionGebruiker();
            PreparedStatement statement = connectDB.prepareStatement("SELECT COUNT(1) FROM docassistent.user WHERE gebruikersnaam = ? AND wachtwoord = ?")) {
            statement.setString(1, gebruikersnaamTextField.getText());
            statement.setString(2, passwordTextField.getText());
            try (ResultSet queryResult = statement.executeQuery()) {
                if (queryResult.next() && queryResult.getInt(1) == 1) {
                    LoginMessageLabel.setText("Ingelogd!");
                    LoginMessageLabel.setTextFill(Color.GREEN);
                    CornerRadii corn = new CornerRadii(4);
                    LoginMessageLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, corn, Insets.EMPTY)));
                    //Nieuwe scene wanneer de login succesvol is.
                    redirectToNewScene();
                    closeCurrentWindow();
                } else {
                    LoginMessageLabel.setText("Ongeldige login, probeer het opnieuw!");
                    LoginMessageLabel.setTextFill(Color.RED);
                    CornerRadii corn = new CornerRadii(4);
                    LoginMessageLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, corn, Insets.EMPTY)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}