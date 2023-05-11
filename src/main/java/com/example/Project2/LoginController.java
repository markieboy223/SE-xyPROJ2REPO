package com.example.Project2;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
    @FXML
    private Button closeButton;
    @FXML
    private Label invalidLoginMessageLabel;
    @FXML
    private TextField gebruikersnaamTextField;
    @FXML
    private PasswordField passwordTextField;

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

    public void validateLogin(){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();

        String verifyLogin = "SELECT count(1) FROM docassistent.user WHERE gebruikersnaam = '" + gebruikersnaamTextField.getText() + "' AND wachtwoord = '" + passwordTextField.getText() + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if (queryResult.getInt(1) == 1){
                    invalidLoginMessageLabel.setText("Ingelogd!");
                    invalidLoginMessageLabel.setTextFill(Color.GREEN);
                    CornerRadii corn = new CornerRadii(4);
                    invalidLoginMessageLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, corn, Insets.EMPTY)));
                } else {
                    invalidLoginMessageLabel.setText("Ongeldige login, probeer het opnieuw!");
                    invalidLoginMessageLabel.setTextFill(Color.RED);
                    CornerRadii corn = new CornerRadii(4);
                    invalidLoginMessageLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, corn, Insets.EMPTY)));
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
