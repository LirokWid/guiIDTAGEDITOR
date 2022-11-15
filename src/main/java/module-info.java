module com.example.guiidtageditor {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.jetbrains.annotations;
    requires java.desktop;

    opens com.example.guiidtageditor to javafx.fxml;
    exports com.example.guiidtageditor;
}