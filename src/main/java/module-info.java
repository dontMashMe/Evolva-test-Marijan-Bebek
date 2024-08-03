module com.example.evolvatestmarijanbebek {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.opencsv;

    opens com.example.evolvatestmarijanbebek to javafx.fxml;

    exports com.example.evolvatestmarijanbebek;
    exports com.example.evolvatestmarijanbebek.controllers;
    exports com.example.evolvatestmarijanbebek.services;
    exports com.example.evolvatestmarijanbebek.utils;
    exports com.example.evolvatestmarijanbebek.models.mappings;
    exports com.example.evolvatestmarijanbebek.models.DAO;

    opens com.example.evolvatestmarijanbebek.controllers to javafx.fxml;
}