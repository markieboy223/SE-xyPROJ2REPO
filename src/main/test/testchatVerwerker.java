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

public class testchatVerwerker {
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
        public void testvindOnderwerp(){
            //Aanmaken chatVerwerker instantie en de onderwerpen uit de database opvragen.
            chatVerwerker cv = new chatVerwerker(cc);
            cv.maakOnderwerpen();

            //Inputs voor een onderwerp dat niet valide en wel valide is.
            String test1 = cv.vindOnderwerp("appels");
            String test2 = cv.vindOnderwerp("medewerkers");

            //Controleren of de 2 outputs correct zijn.
            Assertions.assertNull(test1);
            Assertions.assertEquals("medewerkers", test2);
        }
        @Test
        public void testformuleerAntwoord(){
            //Aanmaken instantie van chatVerwerker en de verwachte outputs
            chatVerwerker cv = new chatVerwerker(cc);
            String expected1 = "Hier heb ik geen informatie over. \n" + "Ik heb alleen kennis over de volgende onderwerpen: \n" + "inkomen\n" + "kosten\n" + "medewerkers\n\n";
            String expected2 = "Q: kosten\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + "Jaartal \n" + "Kosten \n\n";
            String expected3 = "Q: kosten uit 2020\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + "50579\n\n";
            String expected4 = "Q: 50579\n" + "A: Over dit onderwerp heb ik de volgende gegevens:\n" + "2020-01-01\n" + "50579\n\n";

            //De methode testen voor een valide en niet valide input, het detecteren van jaren binnen de input en het kunnen doorvragen.
            String input1 = cv.formuleerAntwoord("appels");
            String input2 = cv.formuleerAntwoord("kosten");
            String input3 = cv.formuleerAntwoord("kosten uit 2020");
            cv.formuleerAntwoord("reset");
            cv.formuleerAntwoord("kosten");
            String input4 = cv.formuleerAntwoord("50579");

            //Checken of de outputs correct zijn.
            Assertions.assertEquals(expected1, input1);
            Assertions.assertEquals(expected2, input2);
            Assertions.assertTrue(cv.heeftJaar);
            Assertions.assertEquals(expected3, input3);
            Assertions.assertEquals(expected4, input4);
        }
    @Test
    public void testsendButtonOnAction(){
        //Klaarmaken van de chatController
        cc.setStartText("yes");

        //chatController instantie inputtekst klaarzetten en outputtekst legen.
        cc.inputTekst.setText("medewerkers");
        cc.outputTekst.clear();
        cc.SendButtonOnAction();

        //Controleren of er naar outputTekst wordt geschreven.
        Assertions.assertNotNull(cc.outputTekst.getText());
    }
}
