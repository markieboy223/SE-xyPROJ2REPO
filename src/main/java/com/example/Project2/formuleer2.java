package com.example.Project2;

import java.sql.SQLException;
import java.util.ArrayList;

public class formuleer2 implements AntwoordStrategie {
    @Override
    public String formuleerAntwoord(String input, chatVerwerker cv) {
        DatabaseConnection connection = new DatabaseConnection();
        StringBuilder antwoord = new StringBuilder();

        String formatText = "";
        boolean buitenTermijn = false;

        cv.keuzes2.clear();
        cv.keuze2 = true;
        try {
            String query;
            if (cv.jaar != null && cv.jaren.contains(cv.jaar + "-01-01")) {
                query = "SELECT " + cv.keuze + " FROM " + cv.onderwerp1 + " WHERE Jaartal = " + cv.jaar;
            } else if (cv.jaar != null && !cv.jaren.contains(cv.jaar + "-01-01")) {
                query = "SELECT Jaartal FROM " + cv.onderwerp1;
                buitenTermijn = true;
            } else {
                query = "SELECT " + cv.keuze + " FROM " + cv.onderwerp1;
            }

            for (String x : cv.tabellenNaam) {
                if (cv.keuze.equalsIgnoreCase(x)) {
                    cv.index = cv.tabellenNaam.indexOf(x);
                }
            }
            ArrayList<String> results = new ArrayList<>();
            cv.executeQueryAndGetResults(connection, query, results);
            cv.keuzes2 = results;
            for (String x : results) {
                formatText = formatText + x + "\n";
            }
            cv.vraagS = cv.vraagS + input;
            String betereKeuzes = formatText.replaceAll("\r", ", ").replaceAll("\n", ", ");
            cv.antwoordS = cv.antwoordS + betereKeuzes;

            if (!buitenTermijn) {
                return ("Q: " + input + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + formatText + "\n");
            } else {
                return ("Q: " + input) + "\n" + "A: Gegegevens over dit jaartal bevinden zich niet in de database:\n"
                        + "Alleen deze jaartallen zijn beschikbaar:\n" + formatText + "\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}