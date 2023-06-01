package com.example.Project2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class chatController extends onderwerp{
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
    private boolean onderwerp2 = false;
    private String onderwerp1 = null;
    private String keuzes = null;
    private String keuze = null;
    private String jaar = null;
    boolean heeftJaar = false;
    private ArrayList<String> check = new ArrayList<>();


    public void VonderwerpOnAction(ActionEvent event){
        onderwerp1 = null;
        onderwerp2 = false;
        keuze = null;
        keuzes = null;
        jaar = null;
        heeftJaar = false;
        check.clear();
        tabellen.clear();
        Honderwerp.setText("");
        chatTab.setText("chat");
        outputTekst.setText("Over welk onderwerp wilt u het hebben?");
    }
    public void SendButtonOnAction(ActionEvent event){
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
                //"select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = " + onderwerp
                //"SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'documentatie' AND TABLE_NAME = 'kosten'"
                //PreparedStatement statement = connectDB.prepareStatement("SELECT * FROM " + onderwerp);
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
                        if (check1.equalsIgnoreCase("jaartal")){
                            getJaartallen(onderwerp1);
                            heeftJaar = true;
                        }
                    }

                    Honderwerp.setText(onderwerp1);
                    chatTab.setText(onderwerp1);
                    outputTekst.setText("Q: " + input + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + keuzes + "\n");
                    inputTekst.clear();
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if (onderwerp2 && keuze != null){
            String keuzes2 = "";
            try (Connection connectDB = connection.getConnectionDoc()){
                PreparedStatement statement = connectDB.prepareStatement("SELECT " + keuze + " FROM " + onderwerp1);
                if (jaar != null && jaren.contains(jaar + "-01-01")){
                    statement = connectDB.prepareStatement("SELECT " + keuze + " FROM " + onderwerp1 + " WHERE Jaartal = " + jaar);
                }
                else if (jaar != null && !jaren.contains(jaar + "-01-01")){
                    statement = connectDB.prepareStatement("SELECT Jaartal FROM " + onderwerp1);
                }
                else{
                    statement = connectDB.prepareStatement("SELECT " + keuze + " FROM " + onderwerp1);
                }
                //"select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = " + onderwerp
                //"SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'documentatie' AND TABLE_NAME = 'kosten'"
                //PreparedStatement statement = connectDB.prepareStatement("SELECT * FROM " + onderwerp);
                int teller = 0;
                try (ResultSet queryResult = statement.executeQuery()) {
                    while (queryResult.next()){
                        teller++;
                        if (teller > 0){
                            antwoord.append(queryResult.getString(1));
                            antwoord.append(" ");
                            keuzes2 = keuzes2 + antwoord.toString() + "\n";
                            antwoord.setLength(0);
                        }
                    }
                    outputTekst.setText("Q: " + input + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + keuzes2 + "\n");
                    inputTekst.clear();
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
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
        else if (onderwerp2){
            for (String woord : apart){
                for (String woord2 : check){
                    if (woord.equalsIgnoreCase(woord2)){
                        return woord;
                    }
                }
            }

        }
        return null;
    }

    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}