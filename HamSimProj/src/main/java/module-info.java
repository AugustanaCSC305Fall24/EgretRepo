module edu.augustana {
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.controls;


    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
}
