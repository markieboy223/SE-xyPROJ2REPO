package com.example.Project2;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ControllerUtils {
    public static void initializeChatView(String fxmlPath, int layout, int userID, String username, String rol, String selectedLanguage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ControllerUtils.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();

            chatController chatControllerInstance = fxmlLoader.getController();
            chatControllerInstance.setUser(userID, username, rol);
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
