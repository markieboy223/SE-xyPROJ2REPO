package com.example.Project2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class opvragenChat {
    private ArrayList<String> vragen = new ArrayList<>();
    private ArrayList<String> antwoorden = new ArrayList<>();
    public void opvragen(int user_id){
        String vraag1;
        String antwoord1;
        DatabaseConnection connection = new DatabaseConnection();
        try (Connection connectDB = connection.getConnectionGebruiker()){
            PreparedStatement statement = connectDB.prepareStatement("SELECT * FROM chatgeschiedenis WHERE user_id = " + user_id);
            try (ResultSet queryResult = statement.executeQuery()) {
                while(queryResult.next()){
                    vraag1 = (queryResult.getString("vraag"));
                    antwoord1 = (queryResult.getString("antwoord"));
                    String antwoord = antwoord1.substring(0, antwoord1.length() - 2);
                    vragen.add(vraag1);
                    antwoorden.add(antwoord);
                }
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> uitvragen(){
        ArrayList<String> totaal = new ArrayList<>();
        for (int i = 0; i < vragen.size(); i++){
            totaal.add(vragen.get(i));
            totaal.add(antwoorden.get(i));
        }
        return totaal;
    }
}
