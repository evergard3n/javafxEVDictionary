package DictionaryApp.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameSwitchController implements Initializable {
    private AnchorPane container;
    @FXML
    private Button multipleChoice, hangman;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        multipleChoice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                String path = "/Views/MultipleChoiceHelper.fxml";
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                    Stage stage = (Stage) multipleChoice.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        hangman.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                String path = "/Views/AdditionGui.fxml";
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                    Stage stage = (Stage) multipleChoice.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @FXML
    private void setNode( Node node ) {


        DictionaryController.getInstance().getContainer().getChildren().clear();
        DictionaryController.getInstance().getContainer().getChildren().add((Node) node);



    }
    @FXML
    private void showComponent( String path ) {
        try {
            AnchorPane Component = FXMLLoader.load(getClass().getResource(path));
            setNode(Component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
