package com.example.Project2;

import com.example.Project2.User;
import com.example.Project2.chatController;
import com.example.Project2.chatVerwerker;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class testopslaanChat {
    private chatController cc = new chatController();
    private User user = new User(3, "test", "21057788@student.hss.nl", "B", "van Biezen", "0642081128", "test", "admin");
    @BeforeEach
    public void setUp() {
        // Initialize the JavaFX toolkit
        Platform.startup(() -> {});
        // Aanmaken van de verschillende javaFx componenten om null pointer errors te voorkomen.
        Label Honderwerp = new Label();
        Tab chatTab = new Tab();
        TextField inputTekst = new TextField();
        TextArea outputTekst = new TextArea();
        cc.setUser(user);
        cc.inputTekst = inputTekst;
        cc.chatTab = chatTab;
        cc.Honderwerp = Honderwerp;
        cc.outputTekst = outputTekst;
    }

    @Test
    public void testslaOp(){
        //Aanmaken chatVerwerker instantie en de onderwerpen uit de database opvragen.
        chatVerwerker cv = new chatVerwerker(cc);
        cv.maakOnderwerpen();

        //Aanroepen cv om de methode opslaan() te runnen.
        cv.vindOnderwerp("appels");
        cv.vindOnderwerp("medewerkers");

        //Controleren of de methode opslaan() is aangeroepen.
        Assertions.assertNotNull(cc.opslaan.userID);

    }
}
