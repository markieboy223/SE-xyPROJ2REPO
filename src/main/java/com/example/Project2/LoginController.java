package com.example.Project2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    @FXML
    private Button closeButton;
    @FXML
    private Label invalidLoginMessageLabel;
    @FXML
    private TextField gebruikersnaamTextField;
    @FXML
    private PasswordField passwordTextField;

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
            invalidLoginMessageLabel.setText("Voer een gebruikersnaam en wachtwoord in.");
            invalidLoginMessageLabel.setTextFill(Color.RED);
            CornerRadii corn = new CornerRadii(4);
            invalidLoginMessageLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, corn, Insets.EMPTY)));
        }
    }
    public void redirectToNewScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chat-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, 520, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void validateLogin() {
        DatabaseConnection connection = new DatabaseConnection();
        try (Connection connectDB = connection.getConnection();
             PreparedStatement statement = connectDB.prepareStatement("SELECT COUNT(1) FROM docassistent.user WHERE gebruikersnaam = ? AND wachtwoord = ?")) {
            statement.setString(1, gebruikersnaamTextField.getText());
            statement.setString(2, passwordTextField.getText());
            try (ResultSet queryResult = statement.executeQuery()) {
                if (queryResult.next() && queryResult.getInt(1) == 1) {
                    invalidLoginMessageLabel.setText("Ingelogd!");
                    invalidLoginMessageLabel.setTextFill(Color.GREEN);
                    CornerRadii corn = new CornerRadii(4);
                    invalidLoginMessageLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, corn, Insets.EMPTY)));
                    //Nieuwe scene wanneer de login succesvol is.
                    redirectToNewScene();
                    closeCurrentWindow();
                } else {
                    invalidLoginMessageLabel.setText("Ongeldige login, probeer het opnieuw!");
                    invalidLoginMessageLabel.setTextFill(Color.RED);
                    CornerRadii corn = new CornerRadii(4);
                    invalidLoginMessageLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, corn, Insets.EMPTY)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
