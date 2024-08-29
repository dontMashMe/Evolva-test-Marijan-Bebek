module com.example.evolvatestmarijanbebek {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.opencsv;
    requires org.jsoup;

    opens com.example.evolvatestmarijanbebek to javafx.fxml;

    exports com.example.evolvatestmarijanbebek;
    exports com.example.evolvatestmarijanbebek.controllers;
    exports com.example.evolvatestmarijanbebek.services;
    exports com.example.evolvatestmarijanbebek.utils;
    exports com.example.evolvatestmarijanbebek.models.mappings;
    exports com.example.evolvatestmarijanbebek.models.DAO;
    exports com.example.evolvatestmarijanbebek.models.reports;

    opens com.example.evolvatestmarijanbebek.controllers to javafx.fxml;
    exports com.example.evolvatestmarijanbebek.services.fileHandling;
    exports com.example.evolvatestmarijanbebek.services.database;
}