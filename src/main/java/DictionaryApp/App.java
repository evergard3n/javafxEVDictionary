package DictionaryApp;

import DictionaryApp.DictionaryCmdLine.DictionaryManagement;
import DictionaryApp.DictionaryCmdLine.DictionaryUtils;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Dictionary;
import java.util.Objects;


public class App extends Application {
    public static void main(String[] args) {
        DictionaryManagement.insertFromFile("src/main/resources/Utils/data.txt");
        System.out.println(DictionaryManagement.numberOfWords());
        launch(args);
    }
    private double xOffset = 0;
    private double yOffset = 0;
    @Override
    public void start(Stage stage) throws Exception {
      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/DictionaryGui.fxml")));
 //      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/HangManGui.fxml")));
        stage.setTitle("Dictionary app");
        stage.initStyle(StageStyle.TRANSPARENT);

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle( MouseEvent event ) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle( MouseEvent event ) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
}
