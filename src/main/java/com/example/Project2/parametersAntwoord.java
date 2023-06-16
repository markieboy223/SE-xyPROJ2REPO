package com.example.Project2;

public class parametersAntwoord {

    private DatabaseConnection connection;
    private boolean buitenTermijn;
    private String inputtekst;
    private String formatText;

    public parametersAntwoord(DatabaseConnection connection, boolean buitenTermijn, String inputtekst, String formatText){
        this.connection = connection;
        this.buitenTermijn = buitenTermijn;
        this.inputtekst = inputtekst;
        this.formatText = formatText;
    }

    public void setBuitenTermijn(boolean buitenTermijn) {
        this.buitenTermijn = buitenTermijn;
    }
    public void setformatText(String formatText) {
        this.formatText = formatText;
    }
    public DatabaseConnection getConnection() {
        return connection;
    }
    public boolean isBuitenTermijn() {
        return buitenTermijn;
    }
    public String getInputtekst() {
        return inputtekst;
    }
    public String getformatText() {
        return formatText;
    }
}
