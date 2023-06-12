package com.example.Project2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class chatController extends onderwerp{
    private opslaanChat opslaan = new opslaanChat();
    private String selectedLanguage;
    @FXML
    private Button closeButton;
    @FXML
    private Button Vonderwerp;
    @FXML
    private TextField inputTekst;
    @FXML
    private Label outputTekst;
    @FXML
    private Label Honderwerp;
    @FXML
    private Button sendButton;
    @FXML
    private Tab chatTab;
    @FXML
    private MenuItem Delete;
    @FXML
    private Menu Chat;
    private boolean onderwerp2 = false;
    private String onderwerp1 = null;
    private String keuzes = null;
    private String keuze = null;
    private String jaar = null;
    boolean heeftJaar = false;
    boolean keuze2 = false;
    boolean checkDoor = false;
    private String vraagS = "";
    private String antwoordS = "";
    private int userID;
    private String userName;
    @FXML
    private Button btnMode;
    @FXML
    private ImageView imgMode;
    @FXML
    private AnchorPane anchorPane;
    private boolean isLightMode = true;
    int index;
    ArrayList<String> keuzes2 = new ArrayList<>();
    ArrayList<String> att = new ArrayList<>();
    private ArrayList<String> check = new ArrayList<>();

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("intellingen-view.fxml"));
            Parent root = fxmlLoader.load();

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
        outputTekst.setTextFill(new Color(0,0,0,1));
    }

    private void setDarkMode() {
        anchorPane.getStylesheets().remove(getClass().getResource("/styles/lightMode.css").toExternalForm());
        anchorPane.getStylesheets().add(getClass().getResource("/styles/darkMode.css").toExternalForm());
        Image image = new Image(getClass().getResource("/Images/ic_light.png").toExternalForm());
        imgMode.setImage(image);
        outputTekst.setTextFill(new Color(1,1,1,1));
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
                Vonderwerp.setText("Change");
                outputTekst.setText("How can i assist you?");

            }
        }
    }

    public void VonderwerpOnAction(ActionEvent event){
        if (vraagS.length() > 0){
            opslaan.opslaan(vraagS, antwoordS, onderwerp1, userID);
            vraagS = "";
            antwoordS = "";
        }
        onderwerp1 = null;
        onderwerp2 = false;
        keuze = null;
        keuzes = null;
        jaar = null;
        heeftJaar = false;
        tabellenInhoud.clear();
        tabellenNaam.clear();
        keuze2 = false;
        index = 0;
        keuzes2.clear();
        check.clear();
        tabellen.clear();
        att.clear();
        Honderwerp.setText("");
        chatTab.setText("chat");
        outputTekst.setText("Over welk onderwerp wilt u het hebben?");
    }
    public void SendButtonOnAction(ActionEvent event){
        if (vraagS.length() > 0){
            opslaan.opslaan(vraagS, antwoordS, onderwerp1, userID);
            vraagS = "";
            antwoordS = "";
        }
        DatabaseConnection connection = new DatabaseConnection();
        String input = inputTekst.getText();
        StringBuilder antwoord = new StringBuilder();

        if (!onderwerp2){
            maakOnderwerpen();
            onderwerp1 = vindOnderwerp(input);
        }
        else {
            keuze = vindOnderwerp(input);
        }

        if (onderwerp1 != null && keuzes == null){
            try (Connection connectDB = connection.getConnectionDoc()){
                PreparedStatement statement = connectDB.prepareStatement("DESCRIBE " + onderwerp1);
                int teller = 0;
                try (ResultSet queryResult = statement.executeQuery()) {
                    while (queryResult.next()){
                        if (teller == 1){
                            antwoord.append(queryResult.getString(1));
                            check.add(String.valueOf(antwoord));
                            antwoord.append(" ");
                            keuzes = antwoord.toString() + "\n";
                            antwoord.setLength(0);
                        }
                        if (teller > 1){
                            antwoord.append(queryResult.getString(1));
                            check.add(String.valueOf(antwoord));
                            antwoord.append(" ");
                            keuzes = keuzes + antwoord.toString() + "\n";
                            antwoord.setLength(0);
                        }
                        teller++;
                    }
                    onderwerp2 = true;
                    for (String check1 : check){
                        getTabbelen(check1, onderwerp1);
                        if (check1.equalsIgnoreCase("Jaartal")){
                            getJaartallen(onderwerp1);
                            heeftJaar = true;
                        }
                    }

                    Honderwerp.setText(onderwerp1);
                    chatTab.setText(onderwerp1);
                    outputTekst.setText("Q: " + input + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + keuzes + "\n");
                    vraagS = vraagS + input;
                    String betereKeuzes = keuzes.replaceAll("\r", ", ").replaceAll("\n", ", ");
                    antwoordS = antwoordS + betereKeuzes;
                    inputTekst.clear();
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        else if (onderwerp2 && keuze != null && !keuze2){
            String fuck = "";
            boolean buitenTermijn = false;
            try (Connection connectDB = connection.getConnectionDoc()){
                PreparedStatement statement = connectDB.prepareStatement("SELECT " + keuze + " FROM " + onderwerp1);
                if (jaar != null && jaren.contains(jaar + "-01-01")){
                    statement = connectDB.prepareStatement("SELECT " + keuze + " FROM " + onderwerp1 + " WHERE Jaartal = " + jaar);
                    for (String x : tabellenNaam){
                        if (keuze.equalsIgnoreCase(x)){
                            index = tabellenNaam.indexOf(x);
                        }
                    }
                    keuze2 = true;
                }
                else if (jaar != null && !jaren.contains(jaar + "-01-01")){
                    statement = connectDB.prepareStatement("SELECT Jaartal FROM " + onderwerp1);
                    buitenTermijn = true;
                    for (String x : tabellenNaam){
                        if (keuze.equalsIgnoreCase(x)){
                            index = tabellenNaam.indexOf(x);
                        }
                    }
                    keuze2 = true;
                }
                else{
                    statement = connectDB.prepareStatement("SELECT " + keuze + " FROM " + onderwerp1);
                    for (String x : tabellenNaam){
                        if (keuze.equalsIgnoreCase(x)){
                            index = tabellenNaam.indexOf(x);
                        }
                    }
                    keuze2 = true;
                }
                int teller = 0;
                try (ResultSet queryResult = statement.executeQuery()) {
                    while (queryResult.next()){
                        teller++;
                        if (teller > 0){
                            antwoord.append(queryResult.getString(1));
                            keuzes2.add(antwoord.toString());
                            antwoord.setLength(0);
                        }
                    }
                    for (String x : keuzes2){
                        fuck = fuck + x + "\n";
                    }
                    if (!buitenTermijn){
                        outputTekst.setText("Q: " + input + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + fuck + "\n");
                    }
                    else{
                        outputTekst.setText("Q: " + input + "\n" + "A: Gegegevens over dit jaartal bevinden zich niet in de database:\n"
                                            + "Alleen deze jaartallen zijn beschikbaar:\n" + fuck + "\n");
                    }
                    vraagS = vraagS + input;
                    String betereKeuzes = fuck.replaceAll("\r", ", ").replaceAll("\n", ", ");
                    antwoordS = antwoordS + betereKeuzes;
                    fuck = "";
                    inputTekst.clear();
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        else if (onderwerp2 && keuze != null){
            String fuck = "";
            boolean buitenTermijn = false;
            if (!checkDoor){
                keuzes2.clear();
                try (Connection connectDB = connection.getConnectionDoc()){
                    PreparedStatement statement = connectDB.prepareStatement("SELECT " + keuze + " FROM " + onderwerp1);
                    if (jaar != null && jaren.contains(jaar + "-01-01")){
                        statement = connectDB.prepareStatement("SELECT " + keuze + " FROM " + onderwerp1 + " WHERE Jaartal = " + jaar);
                        for (String x : tabellenNaam){
                            if (keuze.equalsIgnoreCase(x)){
                                index = tabellenNaam.indexOf(x);
                            }
                        }
                        keuze2 = true;
                    }
                    else if (jaar != null && !jaren.contains(jaar + "-01-01")){
                        statement = connectDB.prepareStatement("SELECT Jaartal FROM " + onderwerp1);
                        buitenTermijn = true;
                        for (String x : tabellenNaam){
                            if (keuze.equalsIgnoreCase(x)){
                                index = tabellenNaam.indexOf(x);
                            }
                        }
                        keuze2 = true;
                    }
                    else{
                        statement = connectDB.prepareStatement("SELECT " + keuze + " FROM " + onderwerp1);
                        for (String x : tabellenNaam){
                            if (keuze.equalsIgnoreCase(x)){
                                index = tabellenNaam.indexOf(x);
                            }
                        }
                        keuze2 = true;
                    }
                    int teller = 0;
                    try (ResultSet queryResult = statement.executeQuery()) {
                        while (queryResult.next()){
                            teller++;
                            if (teller > 0){
                                antwoord.append(queryResult.getString(1));
                                keuzes2.add(antwoord.toString());
                                antwoord.setLength(0);
                            }
                        }
                        for (String x : keuzes2){
                            fuck = fuck + x + "\n";
                        }
                        if (!buitenTermijn){
                            outputTekst.setText("Q: " + input + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + fuck + "\n");
                        }
                        else{
                            outputTekst.setText("Q: " + input + "\n" + "A: Gegegevens over dit jaartal bevinden zich niet in de database:\n"
                                    + "Alleen deze jaartallen zijn beschikbaar:\n" + fuck + "\n");
                        }
                        vraagS = vraagS + input;
                        String betereKeuzes = fuck.replaceAll("\r", ", ").replaceAll("\n", ", ");
                        antwoordS = antwoordS + betereKeuzes;
                        fuck = "";
                        inputTekst.clear();
                    }
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            else{
                int index2 = tabellenInhoud.get(index).indexOf(keuze) + 1;
                try (Connection connectDB = connection.getConnectionDoc()){
                    PreparedStatement statement = connectDB.prepareStatement("SELECT *  FROM " + onderwerp1 + " WHERE " + onderwerp1 + ".id = " + index2);
                    try (ResultSet queryResult = statement.executeQuery()) {
                        while (queryResult.next()){
                            for (String x : tabellenNaam){
                                if (!Objects.equals(x, "id")){
                                    antwoord.append(queryResult.getString(x));
                                    att.add(antwoord.toString());
                                    antwoord.setLength(0);
                                }
                            }
                        }
                        for (String x : att){
                            System.out.println(x);
                            fuck = fuck + x + "\n";
                        }
                        outputTekst.setText("Q: " + input + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + fuck + "\n");
                        vraagS = vraagS + input;
                        String betereKeuzes = fuck.replaceAll("\r", ", ").replaceAll("\n", ", ");
                        antwoordS = antwoordS + betereKeuzes;
                        att.clear();
                        fuck = "";
                        keuzes2.clear();
                        checkDoor = false;
                        keuze2 = false;
                        inputTekst.clear();
                    }
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        else if(!onderwerp2){
            String help = "Hier heb ik geen informatie over. \nIk heb alleen kennis over de volgende onderwerpen: \n";
            String help2 = "";
            for (String dit : tabellen){
                help2 =  help2 + dit + "\n";
            }
            help = help + help2;
            outputTekst.setText(help);
            inputTekst.clear();
        }
        else if(onderwerp2 && keuze == null){
            String help = "Hier heb ik geen informatie over. \nIk heb alleen kennis over de volgende onderwerpen: \n";
            String help2 = "";
            for (String dit : check){
                help2 =  help2 + dit + "\n";
            }
            help = help + help2;
            outputTekst.setText(help);
            inputTekst.clear();
        }

    }
    public String vindOnderwerp(String input){
        String [] apart = input.split(" ");
        jaar = null;
        char [] jaartallen = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int teller = 0;

        if (heeftJaar){
            for (String jaarC : apart){
                if (jaarC.length() == 4){
                    for (int i = 0; i < jaarC.length(); i++){
                        char c = jaarC.charAt(i);
                        for (char j : jaartallen){
                            if (c == j){
                                teller++;
                            }
                        }
                    }
                    if (teller == 4){
                        jaar = jaarC;
                    }
                }
            }
        }


        if (!onderwerp2){
            for (String woord : apart){
                for (String tabel : tabellen){
                    if (woord.toLowerCase().equals(tabel)){
                        return woord;
                    }
                }
            }
        }
        else if (onderwerp2 && !keuze2){
            for (String woord : apart){
                for (String woord2 : check){
                    if (woord.equalsIgnoreCase(woord2)){
                        return woord;
                    }
                }
            }

        }
        else if (onderwerp2 && keuze2){
            String help = "";
            String help2 = "";
            for (int i = 0; i < apart.length; i++){
                String woord = apart[i];
                if (i != apart.length - 1){
                    help = woord + " " + apart[i +1];
                }
                for (String woord2 : keuzes2){
                    if (woord.equalsIgnoreCase(woord2) || help.equalsIgnoreCase(woord2)){
                        if (help.equals(woord2)){
                            return help;
                        }
                        checkDoor = true;
                        return woord2;
                    }
                }
            }

            for (String woord : apart){
                for (String woord2 : check){
                    if (woord.equalsIgnoreCase(woord2)){
                        help = woord2;
                        return help;
                    }
                }
            }
        }
        return null;
    }
    public void setSelectedLanguage(String language) {
        selectedLanguage = language;
        handleLanguageSelection();
    }
    public void setUser(int userID, String userName){
            this.userID = userID;
            this.userName = userName;
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
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}