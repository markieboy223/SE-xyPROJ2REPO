package com.example.Project2;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class LoginController {
    @FXML
    private Button closeButton;
    @FXML
    private Label invalidLoginMessageLabel;

    public void loginButtonOnAction(ActionEvent event){
        invalidLoginMessageLabel.setText("Ongeldige login, probeer het opnieuw.");
        CornerRadii corn = new CornerRadii(4);
        invalidLoginMessageLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, corn, Insets.EMPTY)));

    }

    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
