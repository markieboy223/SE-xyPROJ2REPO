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
    private ArrayList<String> keuzes2 = new ArrayList<>();
    private ArrayList<String> check = new ArrayList<>();

    public chatVerwerker(chatController controller){
        this.controller = controller;
    }
    private void executeQueryAndGetResults(DatabaseConnection connection, String query, ArrayList<String> results) throws SQLException {
        try (Connection connectDB = connection.getConnectionDoc();
             PreparedStatement statement = connectDB.prepareStatement(query);
             ResultSet queryResult = statement.executeQuery()) {
            while (queryResult.next()) {
                results.add(queryResult.getString(1));
            }
        }
    }
    private String formuleer2(parametersAntwoord pa){
        keuzes2.clear();
        keuze2 = true;
        try {
            String query;
            if (jaar != null && jaren.contains(jaar + "-01-01")) {
                query = "SELECT " + keuze + " FROM " + onderwerp1 + " WHERE Jaartal = " + jaar;
            } else if (jaar != null && !jaren.contains(jaar + "-01-01")) {
                query = "SELECT Jaartal FROM " + onderwerp1;
                pa.setBuitenTermijn(true);
            } else {
                query = "SELECT " + keuze + " FROM " + onderwerp1;
            }

            for (String x : tabellenNaam) {
                if (keuze.equalsIgnoreCase(x)) {
                    index = tabellenNaam.indexOf(x);
                }
            }
            ArrayList<String> results = new ArrayList<>();
            executeQueryAndGetResults(pa.getConnection(), query, results);
            keuzes2 = results;
                for (String x : results) {
                    pa.setformatText(pa.getformatText() + x + "\n");
                }
                vraagS = vraagS + pa.getInputtekst();
                String betereKeuzes = pa.getformatText().replaceAll("\r", ", ").replaceAll("\n", ", ");
                antwoordS = antwoordS + betereKeuzes;

            if (!pa.isBuitenTermijn()) {
                return ("Q: " + pa.getInputtekst() + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + pa.getformatText() + "\n");
            } else {
                return ("Q: " + pa.getInputtekst() + "\n" + "A: Gegegevens over dit jaartal bevinden zich niet in de database:\n"
                        + "Alleen deze jaartallen zijn beschikbaar:\n" + pa.getformatText() + "\n");
            }
        } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
    public String formuleerAntwoord_Onderwerp(String inputtekst, DatabaseConnection connection, StringBuilder antwoord){
        if (onderwerp1 != null && keuzes == null){
            try (Connection connectDB = connection.getConnectionDoc()){
                PreparedStatement statement = connectDB.prepareStatement("DESCRIBE " + onderwerp1);
                int teller = 0;
                try (ResultSet queryResult = statement.executeQuery()) {
                    while (queryResult.next()){
                        if (teller > 0){
                            antwoord.append(queryResult.getString(1));
                            check.add(String.valueOf(antwoord));
                            antwoord.append(" ");
                            if (keuzes != null){
                                keuzes = keuzes + antwoord.toString() + "\n";
                            }
                            else {
                                keuzes = antwoord.toString() + "\n";
                            }
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
        else{
            return formuleerAntwoord_Onderwerp2(inputtekst, connection, antwoord);
        }
    }

    public String formuleerAntwoord_Onderwerp2(String inputtekst, DatabaseConnection connection, StringBuilder antwoord){
        if (onderwerp2 && keuze != null && !keuze2){
            String formatText = "";
            boolean buitenTermijn = false;
            return(formuleer2(new parametersAntwoord(connection, buitenTermijn, inputtekst, formatText)));
        }

        else if (onderwerp2 && keuze != null){
            String formatText = "";
            boolean buitenTermijn = false;
            if (!checkDoor){
                return(formuleer2(new parametersAntwoord(connection, buitenTermijn, inputtekst, formatText)));
            }
            else{
                keuzes2.clear();
                int index2 = tabellenInhoud.get(index).indexOf(keuze) + 1;
                try (Connection connectDB = connection.getConnectionDoc()){
                    PreparedStatement statement = connectDB.prepareStatement("SELECT *  FROM " + onderwerp1 + " WHERE " + onderwerp1 + ".id = " + index2);
                    try (ResultSet queryResult = statement.executeQuery()) {
                        while (queryResult.next()){
                            for (String x : tabellenNaam){
                                if (!Objects.equals(x, "id")){
                                    antwoord.append(queryResult.getString(x));
                                    keuzes2.add(antwoord.toString());
                                    antwoord.setLength(0);
                                }
                            }
                        }
                        for (String x : keuzes2){
                            formatText = formatText + x + "\n";
                        }

                        vraagS = vraagS + inputtekst;
                        String betereKeuzes = formatText.replaceAll("\r", ", ").replaceAll("\n", ", ");
                        antwoordS = antwoordS + betereKeuzes;
                        keuzes2.clear();
                        checkDoor = false;
                        keuze2 = false;
                        return ("Q: " + inputtekst + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + formatText + "\n");
                    }
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
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
        String output = formuleerAntwoord_Onderwerp(inputtekst, connection, antwoord);
        if (output != null){
            return output;
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
