package DictionaryApp.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DictionaryController implements Initializable {


    @Override
    public void initialize( URL url , ResourceBundle resourceBundle ) {

        // set on click
        searchWordBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                showComponent("/Views/SearchGui.fxml");
            }
        });
        addWordBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                showComponent("/Views/AdditionGui.fxml");
            }
        });
        gameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                showComponent("/Views/GameSwitch.fxml");
            }
        });
//        translateBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle( ActionEvent event ) {
//                showComponent("/Views/TranslationGui.fxml");
//            }
//        });
        // initial state
        tooltip1.setShowDelay(Duration.seconds(0.5));
        tooltip2.setShowDelay(Duration.seconds(0.5));
        tooltip3.setShowDelay(Duration.seconds(0.5));
        showComponent("/Views/SearchGui.fxml");
        // close app
        closeBtn.setOnMouseClicked(e -> {
            System.exit(0);
        });
    }

    private void setNode( Node node ) {
        container.getChildren().clear();
        container.getChildren().add((Node) node);

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

    private static final class InstanceHolder {
        private static final DictionaryController instance = new DictionaryController();
    }

    public static DictionaryController getInstance() {
        return InstanceHolder.instance;
    }
    @FXML
    public AnchorPane getContainer() {
        return container;
    }
    @FXML
    private Tooltip tooltip1, tooltip2, tooltip3;

    @FXML
    private Button addWordBtn, translateBtn, searchWordBtn, closeBtn, gameBtn;

    @FXML
    private AnchorPane container;

}
