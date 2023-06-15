package com.example.Project2;

public class ChatViewPathResolver {
    public static String resolvePath(int layout) {
        String path;
        if (layout == 2) {
            path = "chat-view3.fxml";
        } else if (layout == 1) {
            path = "chat-view2.fxml";
        } else {
            path = "chat-view.fxml";
        }
        return path;
    }
}