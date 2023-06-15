package com.example.Project2;

public class ChatViewData {
    private int userID;
    private String username;
    private String rol;
    private String selectedLanguage;

    public ChatViewData(int userID, String username, String rol, String selectedLanguage) {
        this.userID = userID;
        this.username = username;
        this.rol = rol;
        this.selectedLanguage = selectedLanguage;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getRol() {
        return rol;
    }

    public String getSelectedLanguage() {
        return selectedLanguage;
    }
}
