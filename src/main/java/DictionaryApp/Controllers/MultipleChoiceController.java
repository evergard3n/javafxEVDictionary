package DictionaryApp.Controllers;

import DictionaryApp.DictionaryCmdLine.DictionaryManagement;
import DictionaryApp.DictionaryCmdLine.Word;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static DictionaryApp.DictionaryCmdLine.Dictionary.dictionary;

public class MultipleChoiceController implements Initializable {
    private static final int numberOfQuestions = 5;
    private int questionIndex = 1, score = 0;
    private String defination, answer;
    @FXML
    private Label questionLabel, scoreLabel;
    @FXML
    private Button choice1, choice2, choice3, choice4, returnBtn, nextBtn;
    private boolean checking = false, correctAns;

    private HashSet<Word> previousAnswers = new HashSet<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String path = "/Views/MultipleChoiceHelper.fxml";
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                    Stage stage = (Stage) returnBtn.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        choice1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!checking) {
                    checkAnswer(choice1.getText(), choice1);
                }
            }
        });
        choice2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!checking) {
                    checkAnswer(choice2.getText(), choice2);
                }
            }
        });
        choice3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!checking) {
                    checkAnswer(choice3.getText(), choice3);
                }
            }
        });
        choice4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!checking) {
                    checkAnswer(choice4.getText(), choice4);
                }
            }
        });
//        choice2.setOnAction(e -> checkAnswer(choice2.getText(), choice2));
//        choice3.setOnAction(e -> checkAnswer(choice3.getText(), choice3));
//        choice4.setOnAction(e -> checkAnswer(choice4.getText(), choice4));
        nextBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                setNextBtnText();
                nextQuestion();


            }
        });
        setNextBtnText();
        displayQuestion();
    }

    private int getRandomIndex(int i) {
        Random random = new Random();
        return random.nextInt(i);
    }

    private void generateQuestion() {
        Word w = dictionary.get(getRandomIndex(dictionary.size()));
        while(previousAnswers.contains(w)) {
            w = dictionary.get(getRandomIndex(dictionary.size()));
        }
        previousAnswers.add(w);
        defination = w.getWord_explain();
        answer = w.getWord_Target();
    }

    private void displayQuestion() {

        HashSet<String> thisQuestionChoices = new HashSet<>();
        if (questionIndex <= numberOfQuestions) {
            generateQuestion();
            questionLabel.setText(defination);
            scoreLabel.setText("Score: " + score);
            ArrayList<Button> b = new ArrayList<>();
            b.add(choice1);
            b.add(choice2);
            b.add(choice3);
            b.add(choice4);
            int i = getRandomIndex(4);
            b.get(i).setText(answer);
            for (Button btn : b) {
                String temp;
                if (btn != b.get(i)) {
                    temp = dictionary.get(getRandomIndex(dictionary.size())).getWord_Target();
                    while (thisQuestionChoices.contains(temp)) {
                        temp = dictionary.get(getRandomIndex(dictionary.size())).getWord_Target();
                    }
                    thisQuestionChoices.add(temp);
                    btn.setText(temp);
                }
            }


        } else {
            questionLabel.setText("Game Over. Your score: " + score);
            disableButtons();
        }

    }



//    private void handleBtn(Button btn) {
//
//        if (checkCorrectAnswer(btn.getText())) {
//            btn.setStyle("-fx-background-color: #1e90ff");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            btn.setStyle("-fx-background-color: #f8f8f8;");
//            checkAnswer(btn.getText());
//        }
//        else {
//            btn.setStyle("-fx-background-color: #ff0000");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            checkAnswer(btn.getText());
//        }
//    }


    private void checkAnswer(String selectedAnswer, Button btn) {

        checking = true;
        if (selectedAnswer.equals(answer)) {
            score++; // Correct answer, increment score
            btn.setStyle("-fx-background-color: #1e90ff");
        } else {
            btn.setStyle("-fx-background-color: #ff0000");
        }
        setNextBtnText();

        // Move to the next question
        //questionIndex++;
    }

    private void nextQuestion() {
        checking = false;
        resetColors();
        questionIndex++;
        displayQuestion();
        setNextBtnText();
    }

    private void resetColors() {
        choice1.setStyle("-fx-background-color: #f8f8f8");
        choice2.setStyle("-fx-background-color: #f8f8f8");
        choice3.setStyle("-fx-background-color: #f8f8f8");
        choice4.setStyle("-fx-background-color: #f8f8f8");
    }

    private void disableButtons() {
        choice1.setDisable(true);
        choice2.setDisable(true);
        choice3.setDisable(true);
        choice4.setDisable(true);
    }
    private void setNextBtnText() {
        if(checking) {
            nextBtn.setText("Tiếp tục");
        }
        else {
            nextBtn.setText("Bỏ qua");
        }
        if(questionIndex > numberOfQuestions) {
            nextBtn.setDisable(true);
        }
    }
}
