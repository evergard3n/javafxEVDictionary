package DictionaryApp.Controllers;
import DictionaryApp.Alerts;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import static DictionaryApp.DictionaryCmdLine.Dictionary.dictionary;
import DictionaryApp.DictionaryCmdLine.*;

public class AdditionController implements Initializable {
    @FXML
    private Button addBtn;
    @FXML
    private TextField insertWordTarget;
    @FXML
    private TextArea insertWordExpl;
    @FXML
    private Label successAlert;
    private Alerts alerts = new Alerts();
    String src = "src/dictionaries.txt";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DictionaryManagement.insertFromFile(src);
        if (insertWordExpl.getText().isEmpty() || insertWordTarget.getText().isEmpty()) {
            addBtn.setDisable(true);
        }
        insertWordTarget.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                addBtn.setDisable(insertWordExpl.getText().isEmpty() || insertWordTarget.getText().isEmpty());
            }
        });
        insertWordExpl.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                addBtn.setDisable(insertWordExpl.getText().isEmpty() || insertWordTarget.getText().isEmpty());
            }
        });
        successAlert.setVisible(false);
    }
    @FXML
    private void handleClickAddBtn() {
        Alert alertConfirmation = alerts.alertConfirmation("Add word" ,
                "Bạn chắc chắn muốn thêm từ này?");
        Optional<ButtonType> option = alertConfirmation.showAndWait();
        // get data from input
        String target = insertWordTarget.getText().trim();
        String meaning = insertWordExpl.getText().trim();

        if (option.get() == ButtonType.OK) {
            Word word = new Word(target, meaning);
            Word w = DictionaryManagement.dictionaryLookUp("target");
            if (w!=null) {
                // find index of word in dictionary

                // show confirmation alert
                Alert selectionAlert = alerts.alertConfirmation("This word already exists" ,
                        "Từ này đã tồn tại.\n" +
                                "Thay thế hoặc bổ sung nghĩa vừa nhập cho nghĩa cũ.");
                // custom button
                selectionAlert.getButtonTypes().clear();
                ButtonType replaceBtn = new ButtonType("Thay thế");
                ButtonType insertBtn = new ButtonType("Bổ sung");
                selectionAlert.getButtonTypes().addAll(replaceBtn , insertBtn , ButtonType.CANCEL);
                Optional<ButtonType> selection = selectionAlert.showAndWait();

                if(selection.get() == replaceBtn) {
                    // replace old meaning, replace this with sqlite later
                    w.setWord_explain(meaning);
                    DictionaryManagement.exportToFile(src);
                    showSuccessAlert();
                }
                if(selection.get() == insertBtn) {
                    // update old meaning, replace with sqlite later
                    String oldMeaning = w.getWord_explain();
                    w.setWord_explain(oldMeaning + "\n=" + meaning);
                    DictionaryManagement.exportToFile(src);
                    showSuccessAlert();
                }
                if(selection.get() == ButtonType.CANCEL){
                    alerts.showAlertInfo("Information" , "Thay đổi không được công nhận.");
                }
            } else {
                dictionary.add(word);
                // succeed
                showSuccessAlert();
            }
            // reset input
            addBtn.setDisable(true);
            resetInput();

        } else if (option.get() == ButtonType.CANCEL) {
            alerts.showAlertInfo("Information" , "Thay đổi không được công nhận.");
        }
    }

    private void resetInput() {
        insertWordExpl.setText("");
        insertWordTarget.setText("");
    }
    private void showSuccessAlert(){
        successAlert.setVisible(true);
        // automatic hide success alert
        DictionaryManagement.setTimeout(() -> successAlert.setVisible(false) , 1500);
    }

}