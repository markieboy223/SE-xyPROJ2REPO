package com.example.Project2;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.Button;

public class chatController extends onderwerp {
    private opslaanChat opslaan = new opslaanChat();
    private opvragenChat opvragen = new opvragenChat();
    private chatVerwerker verwerk;
    private String selectedLanguage;
    @FXML
    private Button closeButton;
    @FXML
    private Button Vonderwerp;
    @FXML
    protected TextField inputTekst;
    @FXML
    protected TextArea outputTekst;
    private ScrollPane scrollDing;
    @FXML
    protected Label Honderwerp;
    @FXML
    private Button sendButton;
    @FXML
    protected Tab chatTab;
    @FXML
    private MenuItem Delete;
    @FXML
    private Menu Chat;
    private String vraagS = "";
    private String antwoordS = "";
    private int userID;
    private String userName;
    private String rol;
    @FXML
    private Button btnMode;
    @FXML
    private ImageView imgMode;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MenuItem register;
    private boolean isLightMode = true;
    int index;
    @FXML
    public void initialize() {
        if (rol != null &&  rol.equalsIgnoreCase("admin")) {
            register.setVisible(true);
        } else {
            register.setVisible(false);
        }
    }
    ArrayList<String> keuzes2 = new ArrayList<>();
    ArrayList<String> att = new ArrayList<>();
    private ArrayList<String> check = new ArrayList<>();

    public int getUserID() {
        return userID;
    }
    public void changeMode(ActionEvent event) {
        isLightMode = !isLightMode;
        if (isLightMode) {
            setLightMode();
        } else {
            setDarkMode();
        }
    }

    public void profileScene(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile-view.fxml"));
            Parent root = fxmlLoader.load();

            ProfileController profileController = fxmlLoader.getController();
            profileController.setUser(userName);
            profileController.initialize();

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void instellingenScene(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Instellingenview.fxml"));
            Parent root = fxmlLoader.load();

            LayoutController layoutController = fxmlLoader.getController();
            layoutController.setSelectedLanguage(selectedLanguage);
            layoutController.setUser(userID, userName, rol);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setLightMode() {
        anchorPane.getStylesheets().remove(getClass().getResource("/styles/darkMode.css").toExternalForm());
        anchorPane.getStylesheets().add(getClass().getResource("/styles/lightMode.css").toExternalForm());
        Image image = new Image(getClass().getResource("/Images/ic_dark.png").toExternalForm());
        imgMode.setImage(image);
        outputTekst.setStyle("-fx-text-fill: black ;");
    }

    private void setDarkMode() {
        anchorPane.getStylesheets().remove(getClass().getResource("/styles/lightMode.css").toExternalForm());
        anchorPane.getStylesheets().add(getClass().getResource("/styles/darkMode.css").toExternalForm());
        Image image = new Image(getClass().getResource("/Images/ic_light.png").toExternalForm());
        imgMode.setImage(image);
        outputTekst.setStyle("-fx-text-fill: white ;");
    }

    public void handleLanguageSelection() {
        if (selectedLanguage != null) {
            if (selectedLanguage.equals("Nederlands")) {
                sendButton.setText("Verstuur");
                closeButton.setText("Afsluiten");
                Vonderwerp.setText("Verander");
                outputTekst.setText("Waar kan ik u mee helpen?");


            } else if (selectedLanguage.equals("English")) {
                sendButton.setText("Send");
                closeButton.setText("Close");
            }
        }
    }
    public void setStartText(String start){
        verwerk = new chatVerwerker(this);
        outputTekst.clear();
        opvragen.opvragen(userID);
        String volgende = "\n";
        ArrayList<String> geschiedenis = opvragen.uitvragen();
        outputTekst.setText("Uw chatgeschiedenis: \n");
        int teller = 0;
        for (String x : geschiedenis){
            if (teller == 0){
                outputTekst.appendText("Q: " + x);
                outputTekst.appendText(volgende);
                teller++;
            }
            else {
                outputTekst.appendText("A: " + x);
                outputTekst.appendText(volgende);
                teller = 0;
            }
        }
        outputTekst.appendText(volgende);
        outputTekst.appendText(start);
        outputTekst.appendText(volgende);
    }

    public void VonderwerpOnAction(ActionEvent event){
        if (verwerk.vraagS.length() > 0){
            opslaan.opslaan(verwerk.vraagS, verwerk.antwoordS, verwerk.onderwerp1, userID);
            verwerk.vraagS = "";
            verwerk.antwoordS = "";
        }
        tabellenInhoud.clear();
        tabellenNaam.clear();
        index = 0;
        tabellen.clear();
        Honderwerp.setText("");
        chatTab.setText("chat");
        setStartText("Over welk onderwerp wilt u het hebben?");
    }
    public void SendButtonOnAction(ActionEvent event){
        outputTekst.setText(verwerk.formuleerAntwoord(inputTekst.getText()));
        inputTekst.clear();
    }

    public void setSelectedLanguage(String language) {
        selectedLanguage = language;
        handleLanguageSelection();
    }
    public void setUser(int userID, String userName, String rol){
            this.userID = userID;
            this.userName = userName;
            this.rol = rol;
    }
    public void createAccountForm(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 800, 600));
            registerStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void closeButtonOnAction(ActionEvent event) {
        Platform.exit();
        if (verwerk != null && verwerk.vraagS.length() > 0){
            opslaan.opslaan(verwerk.vraagS, verwerk.antwoordS, verwerk.onderwerp1, userID);
            verwerk.vraagS = "";
            verwerk.antwoordS = "";
        }
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}