package com.example.Project2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class chatVerwerker extends chatController{
    private chatController controller;
    private opslaanChat opslaan = new opslaanChat();
    private boolean onderwerp2 = false;
    protected String onderwerp1 = null;
    private String keuzes = null;
    private String keuze = null;
    private String jaar = null;
    boolean heeftJaar = false;
    boolean keuze2 = false;
    boolean checkDoor = false;
    protected String vraagS = "";
    protected String antwoordS = "";
    private ArrayList<String> att = new ArrayList<>();
    private ArrayList<String> keuzes2 = new ArrayList<>();
    private ArrayList<String> check = new ArrayList<>();

    public chatVerwerker(chatController controller){
        this.controller = controller;
    }
    public String formuleerAntwoord(String inputtekst){
        if (vraagS.length() > 0){
            opslaan.opslaan(vraagS, antwoordS, onderwerp1, controller.getUserID());
            vraagS = "";
            antwoordS = "";
        }
        DatabaseConnection connection = new DatabaseConnection();
        StringBuilder antwoord = new StringBuilder();

        if (!onderwerp2){
            maakOnderwerpen();
            onderwerp1 = vindOnderwerp(inputtekst);
        }
        else {
            keuze = vindOnderwerp(inputtekst);
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

                    controller.Honderwerp.setText(onderwerp1);
                    controller.chatTab.setText(onderwerp1);
                    vraagS = vraagS + inputtekst;
                    String betereKeuzes = keuzes.replaceAll("\r", ", ").replaceAll("\n", ", ");
                    antwoordS = antwoordS + betereKeuzes;
                    return ("Q: " + inputtekst + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + keuzes + "\n");
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
                    vraagS = vraagS + inputtekst;
                    String betereKeuzes = fuck.replaceAll("\r", ", ").replaceAll("\n", ", ");
                    antwoordS = antwoordS + betereKeuzes;
                    if (!buitenTermijn){
                        return ("Q: " + inputtekst + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + fuck + "\n");
                    }
                    else{
                        return ("Q: " + inputtekst + "\n" + "A: Gegegevens over dit jaartal bevinden zich niet in de database:\n"
                                + "Alleen deze jaartallen zijn beschikbaar:\n" + fuck + "\n");
                    }
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
                        vraagS = vraagS + inputtekst;
                        String betereKeuzes = fuck.replaceAll("\r", ", ").replaceAll("\n", ", ");
                        antwoordS = antwoordS + betereKeuzes;
                        if (!buitenTermijn){
                            return ("Q: " + inputtekst + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + fuck + "\n");
                        }
                        else{
                            return ("Q: " + inputtekst + "\n" + "A: Gegegevens over dit jaartal bevinden zich niet in de database:\n"
                                    + "Alleen deze jaartallen zijn beschikbaar:\n" + fuck + "\n");
                        }
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
                        vraagS = vraagS + inputtekst;
                        String betereKeuzes = fuck.replaceAll("\r", ", ").replaceAll("\n", ", ");
                        antwoordS = antwoordS + betereKeuzes;
                        att.clear();
                        keuzes2.clear();
                        checkDoor = false;
                        keuze2 = false;
                        return ("Q: " + inputtekst + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + fuck + "\n");
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
            return help;
        }
        else if(onderwerp2 && keuze == null){
            String help = "Hier heb ik geen informatie over. \nIk heb alleen kennis over de volgende onderwerpen: \n";
            String help2 = "";
            for (String dit : check){
                help2 =  help2 + dit + "\n";
            }
            help = help + help2;
            return help;
        }
        return "";
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
            char[] jaartallen = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
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

    private String findMatchingWord(String[] words, List<String> options) {
        for (String word : words) {
            if (options.contains(word)) {
                return word;
            }
        }
        return null;
    }

    private String findMatchingWordWithHelp(String[] words, List<String> options) {
        String help = "";
        for (int i = 0; i < words.length - 1; i++) {
            String word = words[i];
            String nextWord = words[i + 1];
            help = word + " " + nextWord;
            String match = findMatchingWord(new String[]{word, help}, options);
            if (match != null) {
                if (match.equals(help)) {
                    return help;
                }
                checkDoor = true;
                return match;
            }
        }
        return findMatchingWord(words, options);
    }
}
