module com.example.demo {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.Project2 to javafx.fxml;
    exports com.example.Project2;
}