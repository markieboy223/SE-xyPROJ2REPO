package com.example.Project2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LayoutController {
    @FXML
    private Button closeButton;
    @FXML
    private Button layout1Button;

    @FXML
    private Button layout2Button;

    @FXML
    private Button layout3Button;
    @FXML
    private Text succesLabel;
    private int userID;
    private String userName;
    private String rol;
    private String selectedLanguage;
    private int layout = 0;
    public void setUser(int userID, String userName, String rol){
        this.userID = userID;
        this.userName = userName;
        this.rol = rol;
    }
    public void setSelectedLanguage(String language) {
        selectedLanguage = language;
    }
    @FXML
    public void layout1ButtonOnAction(ActionEvent event) {
        layout = 0;
        updateLayout();
    }

    @FXML
    public void layout2ButtonOnAction(ActionEvent event) {
        layout = 1;
        updateLayout();
    }

    @FXML
    public void layout3ButtonOnAction(ActionEvent event) {
        layout = 2;
        updateLayout();
    }
    public void updateLayout(){
        DatabaseConnection connection = new DatabaseConnection();
        try (Connection connectDB = connection.getConnectionGebruiker();
             PreparedStatement statement = connectDB.prepareStatement("UPDATE docassistent.user SET layout = ? WHERE gebruikersnaam = ?")) {
            statement.setInt(1, layout);
            statement.setString(2, userName);
            statement.executeUpdate();
            succesLabel.setText("Layout succesvol aangepast naar layout: " + (layout + 1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void redirectToChosenLayout(){
        String path;
        if (layout == 2){
            path = "chat-view3.fxml";
        } else if (layout == 1) {
            path = "chat-view2.fxml";
        } else {
            path = "chat-view.fxml";
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root = fxmlLoader.load();
            chatController chatControllerInstance = fxmlLoader.getController();
            chatControllerInstance.setUser(userID, userName, rol);
            chatControllerInstance.setSelectedLanguage(selectedLanguage);
            chatControllerInstance.initialize();

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
