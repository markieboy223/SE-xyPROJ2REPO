package com.example.Project2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatViewPathResolverTest {
    //Hiermee wordt ook getest of de layout uit layoutcontroller wel correct doorgevoerd zou worden.
    @Test
    public void testResolvePath() {
        // Test case 1: layout = 2
        int layout1 = 2;
        String expectedPath1 = "chat-view3.fxml";
        String actualPath1 = ChatViewPathResolver.resolvePath(layout1);
        assertEquals(expectedPath1, actualPath1);

        // Test case 2: layout = 1
        int layout2 = 1;
        String expectedPath2 = "chat-view2.fxml";
        String actualPath2 = ChatViewPathResolver.resolvePath(layout2);
        assertEquals(expectedPath2, actualPath2);

        // Test case 3: layout = 0 (default case)
        int layout3 = 0;
        String expectedPath3 = "chat-view.fxml";
        String actualPath3 = ChatViewPathResolver.resolvePath(layout3);
        assertEquals(expectedPath3, actualPath3);
    }
}
