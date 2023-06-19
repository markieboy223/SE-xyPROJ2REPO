package com.example.Project2;

public class DutchLanguageSelectionHandler extends LanguageSelectionHandler {
    @Override
    protected void setLabelsAndButtons(LoginController controller, String selectedLanguage) {
        controller.setUsernameLabel("Gebruikersnaam");
        controller.setCloseButtonText("Afsluiten");
        controller.setPasswordLabel("Wachtwoord");
        controller.setLoginButtonText("Inloggen");
    }
}