package com.example.Project2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginControllerTest {

    private LoginController controller;

    @BeforeEach
    public void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Main.class);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("path/to/your/LoginView.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
    }

    @AfterEach
    public void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @Test
    public void testLoginButton(FxRobot robot) {
        robot.clickOn("#gebruikersnaamTextField").write("username");
        robot.clickOn("#passwordTextField").write("password");
        robot.clickOn("#loginButton");

        // Assuming the login success message is displayed in a label with id "LoginMessageLabel"
        assertEquals("Ingelogd!", controller.LoginMessageLabel.getText());
    }

    // Add more test methods for other functionality

}
