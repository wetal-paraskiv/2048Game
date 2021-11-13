module com.example.game2048 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens com.example.game2048 to javafx.fxml;
    exports com.example.game2048;
}