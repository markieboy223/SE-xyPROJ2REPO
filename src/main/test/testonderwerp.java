import com.example.Project2.User;
import com.example.Project2.chatController;
import com.example.Project2.onderwerp;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class testonderwerp {
    private chatController cc = new chatController();
    private User user = new User(3, "test", "21057788@student.hss.nl", "B", "van Biezen", "0642081128", "test", "admin");

    @Test
    public void testmaakOnderwerpen(){
        //Aanmaken van een object onderwerp.
        onderwerp op = new onderwerp();

        //Functie voor het aanmaken van de onderwerpen oproepen.
        op.maakOnderwerpen();

        //Checken of de onderwerpen zijn aangemaakt en in de correcte lijst zijn gezet.
        Assertions.assertNotNull(op.tabellen);
    }
}
