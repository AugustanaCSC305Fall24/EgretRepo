module edu.agustana {
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.controls;


    opens edu.agustana to javafx.fxml;
    exports edu.agustana;
}
