package com.example.Project2;

public class User {
    private int userID;
    private String username;
    private String email;
    private String voornaam;
    private String achternaam;
    private String telefoonnummer;
    private String password;
    private String role;

    public User(int userID ,String username, String email, String voornaam, String achternaam,
                String telefoonnummer, String password, String role) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.telefoonnummer = telefoonnummer;
        this.password = password;
        this.role = role;
    }
    public int getUserID() {
        return userID;
    }
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
