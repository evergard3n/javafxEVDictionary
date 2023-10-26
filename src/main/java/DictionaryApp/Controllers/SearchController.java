package DictionaryApp.Controllers;
import DictionaryApp.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class SearchController implements Initializable{
    @FXML
    private TextField searchTerm;

    @FXML
    private Button  cancelBtn, saveBtn, volumeBtn;

    @FXML
    private Label englishWord, headerList, notAvailableAlert;

    @FXML
    private TextArea explanation;

    @FXML
    private ListView<String> listResults;

    @FXML
    private Pane headerOfExplanation;

    private int firstIndexOfListFound = 0;
    ObservableList<String> list = FXCollections.observableArrayList();
    private int indexOfSelectedWord;
    Alerts alerts = new Alerts();
    String src = "src/dictionaries.txt";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchTerm.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle( KeyEvent keyEvent ) {
                if (searchTerm.getText().isEmpty()) {
                    cancelBtn.setVisible(false);

                } else {
                    cancelBtn.setVisible(true);
                    handleOnKeyTyped();
                }
            }
        });
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                searchTerm.clear();
                notAvailableAlert.setVisible(false);
                cancelBtn.setVisible(false);
                setListDefault(0);
            }
        });
        // initial state
        explanation.setEditable(false);
        saveBtn.setVisible(false);
        cancelBtn.setVisible(false);
        notAvailableAlert.setVisible(false);
    }
    @FXML
    private void handleOnKeyTyped() {
        list.clear();
        String searchKey = searchTerm.getText().trim();
        list = DictionaryManagement.lookUpWord(searchKey);
        if (list.isEmpty()) {
            notAvailableAlert.setVisible(true);
            setListDefault(firstIndexOfListFound);
        } else {
            notAvailableAlert.setVisible(false);
            headerList.setText("Kết quả");
            listResults.setItems(list);
            firstIndexOfListFound = DictionaryManagement.searchForIndex(list.get(0));
        }

    }
    @FXML
    private void handleMouseClickAWord( MouseEvent arg0 ) {
        // search binary
        String selectedWord = listResults.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            indexOfSelectedWord = DictionaryManagement.searchForIndex(selectedWord);
            if (indexOfSelectedWord == -1) {
                return;
            }
            englishWord.setText(dictionary.get(indexOfSelectedWord).getWord_Target());
            explanation.setText(dictionary.get(indexOfSelectedWord).getWord_explain());
            // update status
            headerOfExplanation.setVisible(true);
            explanation.setVisible(true);
            explanation.setEditable(false);
            saveBtn.setVisible(false);
        }
    }
    @FXML
    private void handleClickEditBtn() {
        // update status
        explanation.setEditable(true);
        saveBtn.setVisible(true);
        alerts.showAlertInfo("Information" , "Bạn đã cho phép chỉnh sửa nghĩa từ này!");
    }



    @FXML
    private void handleClickSaveBtn() {
        Alert alertConfirmation = alerts.alertConfirmation("Update" ,
                "Bạn chắc chắn muốn cập nhật nghĩa từ này ?");
        // option != null.
        Optional<ButtonType> option = alertConfirmation.showAndWait();
        if (option.get() == ButtonType.OK) {
            DictionaryManagement.updateWordNP(indexOfSelectedWord , explanation.getText() , src);
            // succeed
            alerts.showAlertInfo("Information" , "Cập nhập thành công!");
        } else{
            alerts.showAlertInfo("Information" , "Thay đổi không được công nhận!");
        }
        // update status
        saveBtn.setVisible(false);
        explanation.setEditable(false);
    }

    @FXML
    private void handleClickDeleteBtn() {
        Alert alertWarning = alerts.alertWarning("Delete" , "Bạn chắc chắn muốn xóa từ này?");
        // option != null.
        alertWarning.getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> option = alertWarning.showAndWait();
        if (option.get() == ButtonType.OK) {
            // delete selected word from dictionary
            DictionaryManagement.deleteWordNP(indexOfSelectedWord , src);
            // refresh everything after deleting word
            refreshAfterDeleting();
            // succeed
            alerts.showAlertInfo("Information" , "Xóa thành công");
        } else {
            alerts.showAlertInfo("Information" , "Thay đổi không được công nhận");
        }
    }

    private void refreshAfterDeleting() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(englishWord.getText())) {
                list.remove(i);
                break;
            }
        }
        listResults.setItems(list);
        // update status
        headerOfExplanation.setVisible(false);
        explanation.setVisible(false);
    }
    private void setListDefault(int index){
        if(index == 0){
            headerList.setText("15 Từ đầu tiên");
        }else{
            headerList.setText("Kết quả liên quan");
        }
        list.clear();
        for (int i = index; i < index + 15; i++) {
            list.add(dictionary.get(i).getWord_Target());
        }
        listResults.setItems(list);
        englishWord.setText(dictionary.get(index).getWord_Target());
        explanation.setText(dictionary.get(index).getWord_explain());
    }
}
