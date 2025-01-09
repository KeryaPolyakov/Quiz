package com.kirillpolyakov.quizspringbootclient.controller;


import com.kirillpolyakov.quizspringbootclient.App;
import com.kirillpolyakov.quizspringbootclient.model.Answer;
import com.kirillpolyakov.quizspringbootclient.model.QuizResult;
import com.kirillpolyakov.quizspringbootclient.model.Result;
import com.kirillpolyakov.quizspringbootclient.retrofit.AnswerRepository;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;
import java.util.prefs.Preferences;

public class GameFormController implements ControllerData<QuizResult> {
    @FXML
    public TabPane tabPaneQuiz;

    private Map<Integer, String> questionAnswer = new HashMap<>();

    private Map<Integer, String> correctAnswers = new HashMap<>();

    private QuizResult quizResult;

    @FXML
    @Override
    public void initData(QuizResult data) {
        quizResult = data;
        List<Result> results = quizResult.getResults();
        for (int i = 0; i < results.size(); i++) {
            Result result = results.get(i);
            Tab tab = new Tab("Qu " + (i + 1));
            VBox vBox = new VBox(new Label(result.getQuestion()));
            ArrayList<String> answers = new ArrayList<>();
            correctAnswers.put(i + 1, result.getCorrectAnswer());
            answers.add(result.getCorrectAnswer());
            answers.addAll(result.getIncorrectAnswers());
            ToggleGroup toggleGroup = new ToggleGroup();
            Collections.shuffle(answers);
            for (String answer : answers) {
                RadioButton radioButton = new RadioButton(answer);
                vBox.getChildren().add(radioButton);
                int count = i + 1;
                radioButton.setOnAction(actionEvent -> questionAnswer.put(count, radioButton.getText()));
                radioButton.setToggleGroup(toggleGroup);
            }
            tab.setContent(vBox);
            this.tabPaneQuiz.getTabs().add(tab);
        }
        Tab tab = new Tab("Results");
        VBox vBox = new VBox();
        vBox.setPrefSize(400, 500);
        Button button = new Button("Check");
        vBox.getChildren().add(button);
        button.setOnAction(actionEvent -> {
            try {
                if (questionAnswer.size() < results.size()) {
                    App.showInfo("Error", "Not enough answers", Alert.AlertType.ERROR);
                } else {
                    Preferences preferences = Preferences.userNodeForPackage(App.class);
                    Label label = new Label("Statistics");
                    vBox.getChildren().add(label);
                    int count = 0;
                    int correct = 0;
                    for (Map.Entry<Integer, String> numAns : questionAnswer.entrySet()) {
                        String res = "Question" + (1 + count++) + ": ";
                        boolean isCorrect = false;
                        if (numAns.getValue().equals(correctAnswers.get(count))) {
                            res += "+";
                            correct++;
                            isCorrect = true;
                        } else {
                            res += "-";
                        }
                        new AnswerRepository(preferences.get("username", ""),
                                preferences.get("password", ""))
                                .add(new Answer(correctAnswers.get(count), isCorrect), results.get(count - 1).getId());
                        if (preferences.getBoolean("check", false)) {
                            res += " (" + correctAnswers.get(count) + ')';
                        }
                        vBox.getChildren().add(new Label(res));
                    }
                    vBox.getChildren().add(new Label("Correct/Incorrect: " + correct + "/" + (count - correct)));
                    vBox.getChildren().add(new Label("CorrectAnswerRate: "
                            + String.format("%.2f", 100.0 / count * correct) + "%"));
                }
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
                App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        });
        tab.setContent(vBox);
        this.tabPaneQuiz.getTabs().add(tab);
    }
}
