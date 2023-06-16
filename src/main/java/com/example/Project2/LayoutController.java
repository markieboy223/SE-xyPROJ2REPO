package com.example.Project2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    private User user;
    private String selectedLanguage;
    private int layout = 0;
    public void setUser(User user) {
        this.user = user;
    }
    public void setSelectedLanguage(String language) {
        selectedLanguage = language;
    }
    @FXML
    public void layout1ButtonOnAction() {
        layout = 0;
        updateLayout();
    }

    @FXML
    public void layout2ButtonOnAction() {
        layout = 1;
        updateLayout();
    }

    @FXML
    public void layout3ButtonOnAction() {
        layout = 2;
        updateLayout();
    }
    public void updateLayout(){
        DatabaseConnection connection = new DatabaseConnection();
        try (Connection connectDB = connection.getConnectionGebruiker();
             PreparedStatement statement = connectDB.prepareStatement("UPDATE docassistent.user SET layout = ? WHERE gebruikersnaam = ?")) {
            statement.setInt(1, layout);
            statement.setString(2, user.getUsername());
            statement.executeUpdate();
            succesLabel.setText("Layout succesvol aangepast naar layout: " + (layout + 1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void redirectToChosenLayout() {
        String path = ChatViewPathResolver.resolvePath(layout);
        ControllerUtils.initializeChatView(path, user, selectedLanguage);
        closeCurrentWindow();
    }
    public void closeCurrentWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
