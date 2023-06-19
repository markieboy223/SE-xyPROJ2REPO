package com.example.Project2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class formuleer3 implements AntwoordStrategie {
    @Override
    public String formuleerAntwoord(String input, chatVerwerker cv) {
        DatabaseConnection connection = new DatabaseConnection();
        StringBuilder antwoord = new StringBuilder();
        String formatText = "";

        cv.keuzes2.clear();
        int index2 = cv.tabellenInhoud.get(cv.index).indexOf(cv.keuze) + 1;
        try (Connection connectDB = connection.getConnectionDoc()){
            PreparedStatement statement = connectDB.prepareStatement("SELECT *  FROM " + cv.onderwerp1 + " WHERE " + cv.onderwerp1 + ".id = " + index2);
            try (ResultSet queryResult = statement.executeQuery()) {
                while (queryResult.next()){
                    for (String x : cv.tabellenNaam){
                        if (!Objects.equals(x, "id")){
                            antwoord.append(queryResult.getString(x));
                            cv.keuzes2.add(antwoord.toString());
                            antwoord.setLength(0);
                        }
                    }
                }
                for (String x : cv.keuzes2){
                    formatText = formatText + x + "\n";
                }

                cv.vraagS = cv.vraagS + input;
                String betereKeuzes = formatText.replaceAll("\r", ", ").replaceAll("\n", ", ");
                cv.antwoordS = cv.antwoordS + betereKeuzes;
                cv.keuzes2.clear();
                cv.checkDoor = false;
                cv.keuze2 = false;
                return ("Q: " + input + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + formatText + "\n");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
