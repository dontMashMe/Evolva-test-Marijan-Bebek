module com.example.evolvatestmarijanbebek {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.evolvatestmarijanbebek to javafx.fxml;
    exports com.example.evolvatestmarijanbebek;
}