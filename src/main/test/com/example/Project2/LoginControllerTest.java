package com.example.Project2;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginControllerTest {
    private LoginController controller = new LoginController();

    @BeforeEach
    public void setUp() {
        // Initialize the JavaFX toolkit
        Platform.startup(() -> {});
    }

    @Test
    public void testHandleFailedLogin() {
        // Initialiseren van de Label
        Label loginMessageLabel = new Label();
        controller.LoginMessageLabel = loginMessageLabel;

        // Methode aanroepen
        controller.handleFailedLogin();

        // Assert
        assertEquals("Ongeldige login, probeer het opnieuw!", loginMessageLabel.getText());
        assertEquals(Color.RED, loginMessageLabel.getTextFill());
    }
    @Test
    public void testSetLoginMessage() {
        // Initialiseren van de Label
        Label loginMessageLabel = new Label();
        controller.LoginMessageLabel = loginMessageLabel;

        // Methode aanroepen met eigen message en kleur
        controller.setLoginMessage("Login succesvol", Color.GREEN);

        // Assert
        assertEquals("Login succesvol", loginMessageLabel.getText());
        assertEquals(Color.GREEN, loginMessageLabel.getTextFill());
        Background background = loginMessageLabel.getBackground();
        assertNotNull(background);
        BackgroundFill backgroundFill = background.getFills().get(0);
        assertEquals(Color.WHITE, backgroundFill.getFill());
        CornerRadii cornerRadii = backgroundFill.getRadii();
        assertNotNull(cornerRadii);
    }
    @Test
    public void testHandleLanguageSelection() {
        // Set up
        ComboBox<String> languageComboBox = new ComboBox<>();
        Label usernameLabel = new Label();
        Button closeButton = new Button();
        Label passwordLabel = new Label();
        Button loginButton = new Button();

        LoginController controller = new LoginController();
        controller.languageComboBox = languageComboBox;
        controller.usernameLabel = usernameLabel;
        controller.closeButton = closeButton;
        controller.passwordLabel = passwordLabel;
        controller.loginButton = loginButton;

        // Geselecteerde taal: Nederlands
        languageComboBox.setValue("Nederlands");
        controller.handleLanguageSelection();

        // Assert
        assertEquals("Gebruikersnaam", usernameLabel.getText());
        assertEquals("Afsluiten", closeButton.getText());
        assertEquals("Wachtwoord", passwordLabel.getText());
        assertEquals("Inloggen", loginButton.getText());

        // Geselecteerde taal: Engels
        languageComboBox.setValue("English");
        controller.handleLanguageSelection();

        // Assert
        assertEquals("Username", usernameLabel.getText());
        assertEquals("Close", closeButton.getText());
        assertEquals("Password", passwordLabel.getText());
        assertEquals("Login", loginButton.getText());

    }

}
