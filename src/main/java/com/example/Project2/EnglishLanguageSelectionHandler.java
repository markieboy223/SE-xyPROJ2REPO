package com.example.Project2;

public class EnglishLanguageSelectionHandler extends LanguageSelectionHandler {
    @Override
    protected void setLabelsAndButtons(LoginController controller, String selectedLanguage) {
        controller.setUsernameLabel("Username");
        controller.setCloseButtonText("Close");
        controller.setPasswordLabel("Password");
        controller.setLoginButtonText("Login");
    }
}
