import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField field = new TextField();
        field.setText("Voer hier uw naampie in");

        Button btn = new Button();
        btn.setText("Mogge");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("hallo " + field.getText() + "!");
            }
        });
        BorderPane root = new BorderPane();
        root.setBottom(btn);

        root.setTop(field);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}