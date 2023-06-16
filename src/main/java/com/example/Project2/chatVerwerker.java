package com.example.Project2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class chatVerwerker extends chatController{
    public chatController controller;
    public opslaanChat opslaan = new opslaanChat();
    public boolean onderwerp2 = false;
    public String onderwerp1 = null;
    public String keuzes = null;
    public String keuze = null;
    public String jaar = null;
    public boolean heeftJaar = false;
    boolean keuze2 = false;
    boolean checkDoor = false;
    protected String vraagS = "";
    protected String antwoordS = "";
    public ArrayList<String> keuzes2 = new ArrayList<>();
    public ArrayList<String> check = new ArrayList<>();
    public chatVerwerker(chatController controller){
        this.controller = controller;
    }
    public void executeQueryAndGetResults(DatabaseConnection connection, String query, ArrayList<String> results) throws SQLException {
        try (Connection connectDB = connection.getConnectionDoc();
             PreparedStatement statement = connectDB.prepareStatement(query);
             ResultSet queryResult = statement.executeQuery()) {
            while (queryResult.next()) {
                results.add(queryResult.getString(1));
            }
        }
    }
    public String formuleerAntwoord(String inputtekst){
        if (vraagS.length() > 0){
            opslaan.opslaan(vraagS, antwoordS, onderwerp1, controller.getUserID());
            vraagS = "";
            antwoordS = "";
        }
        AntwoordStrategie antwoord1 = new formuleer1();
        AntwoordStrategie antwoord2 = new formuleer2();
        AntwoordStrategie antwoord3 = new formuleer3();

        begin(inputtekst);

        if ((onderwerp1 != null && keuzes == null) && antwoord1.formuleerAntwoord(inputtekst, this) != null){
            return antwoord1.formuleerAntwoord(inputtekst, this);
        }
        else if ((onderwerp2 && keuze != null && !keuze2) && antwoord2.formuleerAntwoord(inputtekst, this) != null){
            return antwoord2.formuleerAntwoord(inputtekst, this);
        }
        else if (onderwerp2 && keuze != null){
            if (!checkDoor && antwoord2.formuleerAntwoord(inputtekst, this) != null){
                return antwoord2.formuleerAntwoord(inputtekst, this);
            }
            else if (antwoord3.formuleerAntwoord(inputtekst, this) != null){
                return antwoord3.formuleerAntwoord(inputtekst, this);
            }
        }
        return geenInformatie();
    }
    private String geenInformatie(){
        ArrayList<String> lijst;
        if (!onderwerp2){
            lijst = tabellen;
        }
        else if(keuze ==  null){
            lijst = check;
        }
        else{
            return "";
        }
        String help = "Hier heb ik geen informatie over. \nIk heb alleen kennis over de volgende onderwerpen: \n";
        String help2 = "";
        for (String dit : lijst){
            help2 =  help2 + dit + "\n";
        }
        help = help + help2 + "\n";
        return help;
    }
    public void begin(String inputtekst){
        if (!onderwerp2){
            maakOnderwerpen();
            onderwerp1 = vindOnderwerp(inputtekst);
        }
        else {
            keuze = vindOnderwerp(inputtekst);
        }
    }
    public String vindOnderwerp(String input) {
        String[] apart = input.split(" ");
        jaar = validateYear(apart);

        if (!onderwerp2) {
            return findMatchingWord(apart, tabellen);
        }
        if (!keuze2) {
            return findMatchingWord(apart, check);
        }
        return findMatchingWordWithHelp(apart, keuzes2);
    }
    private String validateYear(String[] apart) {
        if (heeftJaar) {
            char[] jaartallen = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
            int teller = 0;
            for (String jaarC : apart) {
                if (jaarC.length() == 4) {
                    for (int i = 0; i < jaarC.length(); i++) {
                        char c = jaarC.charAt(i);
                        for (char j : jaartallen) {
                            if (c == j) {
                                teller++;
                            }
                        }
                    }
                    if (teller == 4) {
                        return jaarC;
                    }
                }
            }
        }
        return null;
    }
    private String findMatchingWord(String[] words, ArrayList<String> options) {
        for (String word : words) {
            for (String optie : options){
                if (optie.equalsIgnoreCase(word)) {
                    return word;
                }
            }
        }
        return null;
    }
    private String findMatchingWordWithHelp(String[] words, ArrayList<String> options) {
        String help = "";
        int teller = 0;
        String nextWord = "";
        for (String word : words) {
            while (teller < (words.length - 1)){
                nextWord = words[teller + 1];
                teller++;
            }
            help = word + " " + nextWord;
            for (String optie : options){
                if (optie.equalsIgnoreCase(word)) {
                    checkDoor = true;
                    return optie;
                } else if (optie.equalsIgnoreCase(help)) {
                    checkDoor = true;
                    return optie;
                }
            }
        }
        return null;
    }
}
