package com.example.Project2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class onderwerp {
    protected ArrayList<String> tabellenNaam = new ArrayList<>();
    protected ArrayList<ArrayList<String>> tabellenInhoud = new ArrayList<>();
    public ArrayList<String> tabellen = new ArrayList<>();
    protected ArrayList<String> jaren = new ArrayList<>();

    public void maakOnderwerpen() {
        tabellen.clear();
        fetchTabellen("SELECT DISTINCT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'documentatie'", tabellen);
    }

    public void getJaartallen(String onderwerp) {
        String query = "SELECT Jaartal FROM " + onderwerp;
        fetchTabellen(query, jaren);
    }

    public void getTabbelen(String tabNaam, String onderwerp) {
        ArrayList<String> inhoudC = new ArrayList<>();

        if (tabellenNaam.size() == 0) {
            tabellenNaam.add("id");
            String query = "SELECT 'id' FROM " + onderwerp;
            fetchTabellen(query, inhoudC);
            tabellenInhoud.add(inhoudC);
            inhoudC.clear();
        }

        if (!tabellenNaam.contains(tabNaam)){
            tabellenNaam.add(tabNaam);
        }
        String query = "SELECT " + tabNaam + " FROM " + onderwerp;
        fetchTabellen(query, inhoudC);
        tabellenInhoud.add(inhoudC);
    }

    private void fetchTabellen(String query, ArrayList<String> list) {
        DatabaseConnection connection = new DatabaseConnection();
        StringBuilder resultBuilder = new StringBuilder();

        try (Connection connectDB = connection.getConnectionDoc()) {
            PreparedStatement statement = connectDB.prepareStatement(query);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    resultBuilder.append(resultSet.getString(1));
                    list.add(String.valueOf(resultBuilder));
                    resultBuilder.setLength(0);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}