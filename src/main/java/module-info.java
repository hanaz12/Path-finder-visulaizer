module com.example.pathfinder {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.example.pathfinder to javafx.fxml;
    exports com.example.pathfinder;
}