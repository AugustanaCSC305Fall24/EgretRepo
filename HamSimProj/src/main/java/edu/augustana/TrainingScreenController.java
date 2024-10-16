package edu.augustana;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;


import java.io.IOException;




public class TrainingScreenController {


    @FXML
    private HBox mainHbox;

    @FXML
    private BorderPane trainingBorderPane;

    @FXML
    public TabPane trainingScreenTabPane;

    @FXML
    private ImageView radioImage;


    @FXML
    public void initialize() throws IOException {

        try {
            // Load TrainingScreen1.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrainingScreen1.fxml"));
            TabPane trainingTabPane = loader.load();

            TrainingScreen1Controller otherController = loader.getController();

            // Inject this controller (Controller1) into the second controller (OtherController)
            otherController.setTrainingScreenController(this);


            // Remove the old TabPane and add the new one
            trainingScreenTabPane = trainingTabPane;
            mainHbox.getChildren().add(trainingTabPane);

            Image radioPic = new Image("file:C:\\Users\\camio\\HamSimProject\\HamSim\\EgretRepo\\HamSimProj\\src\\main\\resources\\assets\\Radio1.png");
//            radioImage.setScaleX(3);
//            radioImage.setScaleX(3);
            radioImage.setImage(radioPic);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public void addToMainHbox(Node node){
        mainHbox.getChildren().remove(trainingScreenTabPane);
        mainHbox.getChildren().add(node);
        trainingScreenTabPane = (TabPane) node;
    }



}
