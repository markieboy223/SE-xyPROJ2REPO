package com.example.Project2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class formuleer1 implements AntwoordStrategie {
    @Override
    public String formuleerAntwoord(String input, chatVerwerker cv) {
        DatabaseConnection connection = new DatabaseConnection();
        StringBuilder antwoord = new StringBuilder();

        cv.keuzes = "";
        cv.check.clear();
        try (Connection connectDB = connection.getConnectionDoc()){
            PreparedStatement statement = connectDB.prepareStatement("DESCRIBE " + cv.onderwerp1);
            int teller = 0;
            try (ResultSet queryResult = statement.executeQuery()) {
                while (queryResult.next()){
                    if (teller > 0){
                        antwoord.append(queryResult.getString(1));
                        cv.check.add(String.valueOf(antwoord));
                        antwoord.append(" ");
                        if (cv.keuzes != null){
                            cv.keuzes = cv.keuzes + antwoord.toString() + "\n";
                        }
                        else {
                            cv.keuzes = antwoord.toString() + "\n";
                        }
                        antwoord.setLength(0);
                    }
                    teller++;
                }
                cv.onderwerp2 = true;
                for (String check1 : cv.check){
                    cv.getTabbelen(check1, cv.onderwerp1);
                    if (check1.equalsIgnoreCase("Jaartal")){
                        cv.getJaartallen(cv.onderwerp1);
                        cv.heeftJaar = true;
                    }
                }

                cv.controller.Honderwerp.setText(cv.onderwerp1);
                cv.controller.chatTab.setText(cv.onderwerp1);
                cv.vraagS = cv.vraagS + input;
                String betereKeuzes = cv.keuzes.replaceAll("\r", ", ").replaceAll("\n", ", ");
                cv.antwoordS = cv.antwoordS + betereKeuzes;
                return ("Q: " + input + "\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + cv.keuzes + "\n");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

