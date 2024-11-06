package edu.augustana;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static edu.augustana.CWHandler.startTimer;
import static edu.augustana.CWHandler.stopTimer;
import static edu.augustana.Radio.*;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Stage windowStage;

    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("mainUI"), 1280, 720);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        windowStage = stage;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        System.out.println("Now using 10m band");
        launch();
    }



}