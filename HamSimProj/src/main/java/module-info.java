module edu.augustana {
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.controls;
    requires javax.websocket.api;
    requires tyrus.client;
    requires java.sql;


    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
}
