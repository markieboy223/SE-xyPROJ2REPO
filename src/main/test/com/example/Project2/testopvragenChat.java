package com.example.Project2;

import com.example.Project2.User;
import com.example.Project2.chatController;
import com.example.Project2.opvragenChat;
import javafx.application.Platform;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class testopvragenChat {
    private chatController cc = new chatController();
    private User user = new User(3, "test", "21057788@student.hss.nl", "B", "van Biezen", "0642081128", "test", "admin");
    @BeforeEach
    public void setUp() {
        // Initialize the JavaFX toolkit
        Platform.startup(() -> {});
    }
    @Test
    public void testuitvragen(){
        //Aanmaken user en instantie van opvragenChat.
        cc.setUser(user);
        opvragenChat oc = new opvragenChat();

        //Opvragen van de chatgeschiedenis.
        oc.opvragen(cc.getUserID());

        //Controleren of de chatgeschiedenis wordt gereturned en of dit wordt opgesplitst in vragen en antwoorden.
        Assertions.assertNotNull(oc.uitvragen());
        Assertions.assertEquals(oc.vragen.size(), (oc.uitvragen().size() / 2));
    }
}

