package com.example.Project2;

public abstract class LanguageSelectionHandler {
    protected abstract void setLabelsAndButtons(LoginController controller, String selectedLanguage);

    public final void handleLanguageSelection(LoginController controller, String selectedLanguage) {
        setLabelsAndButtons(controller, selectedLanguage);
    }
}
