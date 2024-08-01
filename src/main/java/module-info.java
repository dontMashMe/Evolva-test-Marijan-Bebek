module com.example.evolvatestmarijanbebek {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.evolvatestmarijanbebek to javafx.fxml;
    exports com.example.evolvatestmarijanbebek;
    exports com.example.evolvatestmarijanbebek.controllers;
    exports com.example.evolvatestmarijanbebek.services;
    //exports com.example.evolvatestmarijanbebek.models;
    opens com.example.evolvatestmarijanbebek.controllers to javafx.fxml;
}