package com.example.Project2;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;

public class LoginController {
    @FXML
    public Button closeButton;
    @FXML
    public Button loginButton;
    @FXML
    public Label LoginMessageLabel;
    @FXML
    public Label usernameLabel;
    @FXML
    private TextField gebruikersnaamTextField;
    @FXML
    public Label passwordLabel;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    public ComboBox<String> languageComboBox;
    private int userID = -5;
    private String username = "";
    private String email;
    private String voornaam;
    private String achternaam;
    private String telefoonnummer;
    private String password;
    private int layout = 0;
    private String rol = "";
    private LanguageSelectionHandler languageSelectionHandler;

    public void initialize() {
        // Add language options to the ComboBox
        languageComboBox.getItems().addAll("Nederlands", "English");
        // Set the default selected language
        languageComboBox.getSelectionModel().selectFirst();

        String defaultLanguage = languageComboBox.getValue();
        setLanguageSelectionHandler(defaultLanguage);
    }

    public void handleLanguageSelection() {
        String selectedLanguage = languageComboBox.getValue();
        setLanguageSelectionHandler(selectedLanguage);
        languageSelectionHandler.handleLanguageSelection(this, selectedLanguage);
    }

    private void setLanguageSelectionHandler(String selectedLanguage) {
        if (selectedLanguage.equals("Nederlands")) {
            languageSelectionHandler = new DutchLanguageSelectionHandler();
        } else if (selectedLanguage.equals("English")) {
            languageSelectionHandler = new EnglishLanguageSelectionHandler();
        }
    }

    public void setUsernameLabel(String label) {
        usernameLabel.setText(label);
    }

    public void setCloseButtonText(String text) {
        closeButton.setText(text);
    }

    public void setPasswordLabel(String label) {
        passwordLabel.setText(label);
    }

    public void setLoginButtonText(String text) {
        loginButton.setText(text);
    }

    public void closeButtonOnAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void closeCurrentWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(){
        if (!gebruikersnaamTextField.getText().isBlank() && !passwordTextField.getText().isBlank()){
            validateLogin();
        } else {
            setLoginMessage("Voer een gebruikersnaam en wachtwoord in.", Color.RED);
        }
    }

    public void redirectToNewScene() {
        User user = new User(userID, username, email, voornaam, achternaam, telefoonnummer, password, rol);
        String path = ChatViewPathResolver.resolvePath(layout);
        ControllerUtils.initializeChatView(path, user, languageComboBox.getValue());
        closeCurrentWindow();
    }

    public void validateLogin() {
        DatabaseConnection connection = new DatabaseConnection();
        try (Connection connectDB = connection.getConnectionGebruiker();
             PreparedStatement statement = connectDB.prepareStatement("SELECT COUNT(1), id, email, voornaam, achternaam, telefoonnummer, rol, layout FROM docassistent.user WHERE gebruikersnaam = ? AND wachtwoord = ?")) {
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
        userID = queryResult.getInt("id");
        username = gebruikersnaamTextField.getText();
        password = passwordTextField.getText();
        email = queryResult.getString("email");
        voornaam = queryResult.getString("voornaam");
        achternaam = queryResult.getString("achternaam");
        telefoonnummer = queryResult.getString("telefoonnummer");
        rol = queryResult.getString("rol");
        layout = queryResult.getInt("layout");

        setLoginMessage("Ingelogd!", Color.GREEN);
        redirectToNewScene();
        closeCurrentWindow();
    }

    public void handleFailedLogin() {
        setLoginMessage("Ongeldige login, probeer het opnieuw!", Color.RED);
    }

    public void setLoginMessage(String message, Color color) {
        LoginMessageLabel.setText(message);
        LoginMessageLabel.setTextFill(color);
        CornerRadii corn = new CornerRadii(4);
        LoginMessageLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, corn, Insets.EMPTY)));
    }
}