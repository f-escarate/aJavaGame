module com.tomandmax {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.desktop;


    exports com.tomandmax;
    exports com.tomandmax.mainCharacters;
    exports com.tomandmax.enemies;
    exports com.tomandmax.attacks;
    exports com.tomandmax.items;

    opens com.controller to javafx.fxml;
    exports com.controller;
    exports com.controller.exceptions;
    exports com.controller.phases;
    exports com.GUI;
    opens com.GUI to javafx.fxml;
}