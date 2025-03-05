module com.example.remake {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.remake to javafx.fxml;
    opens Model to javafx.base;
    exports com.example.remake;
    exports Controllers;
    opens Controllers to javafx.fxml;
    exports Database;
    opens Database to javafx.fxml;
}