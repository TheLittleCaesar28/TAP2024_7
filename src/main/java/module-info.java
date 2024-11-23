module com.example.tap2024_7 {
    requires javafx.fxml;
    requires javafx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml;
    requires java.sql;



    opens com.example.tap2024_7 to javafx.fxml;
    opens com.example.tap2024_7.VISTAS to javafx.fxml;
    exports com.example.tap2024_7;
    exports com.example.tap2024_7.VISTAS;
    exports com.example.tap2024_7.MODELS;

}