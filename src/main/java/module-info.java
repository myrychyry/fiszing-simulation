module com.example.fiszingsymulatjon {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fiszingsymulatjon to javafx.fxml;
    exports com.example.fiszingsymulatjon;
}