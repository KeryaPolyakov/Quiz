package com.kirillpolyakov.quizspringbootadminclient.controller;

import com.kirillpolyakov.quizspringbootadminclient.App;
import com.kirillpolyakov.quizspringbootadminclient.model.Answer;
import com.kirillpolyakov.quizspringbootadminclient.model.Request;
import com.kirillpolyakov.quizspringbootadminclient.model.SimpleUser;
import com.kirillpolyakov.quizspringbootadminclient.retrofit.AnswerRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.prefs.Preferences;

public class AnswersController implements ControllerData<SimpleUser> {
    @FXML
    public TableView<Answer> tableViewAnswers;
    @FXML
    public Label labelCorIncor;
    @FXML
    public Label labelProcent;

    @Override
    public void initData(SimpleUser data) {
        Preferences preferences = Preferences.userNodeForPackage(App.class);
        try {
            AnswerRepository answerRepository = new AnswerRepository(preferences.get("username", ""),
                    preferences.get("password", ""));
            TableColumn<Answer, String> question = new TableColumn<>("Question");
            TableColumn<Answer, String> answer = new TableColumn<>("Answer");
            TableColumn<Answer, String> isCorrect = new TableColumn<>("IsCorrect");
            TableColumn<Answer, String> dateTime = new TableColumn<>("DateTime");
            question.setPrefWidth(150);
            question.setCellValueFactory(requestStringCellDataFeatures ->
                    new SimpleStringProperty(requestStringCellDataFeatures.getValue().getResult().getQuestion()));
            answer.setCellValueFactory(new PropertyValueFactory<>("answer"));
            isCorrect.setCellValueFactory(requestStringCellDataFeatures -> {
                if (requestStringCellDataFeatures.getValue().isCorrect()) {
                    return new SimpleStringProperty("YES");
                }
                return new SimpleStringProperty("NO");
            });
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            dateTime.setCellValueFactory(requestStringCellDataFeatures ->
                    new SimpleStringProperty(
                            dateTimeFormatter.format(requestStringCellDataFeatures.getValue().getLocalDateTime())));
            tableViewAnswers.getColumns().addAll(question, answer, isCorrect, dateTime);
            tableViewAnswers.setItems(FXCollections.observableList(answerRepository.getAllByUserId(data.getId())));
            List<Answer> answerList = answerRepository.getAllByUserId(data.getId());
            long cor = answerList.stream().filter(Answer::isCorrect).count();
            labelCorIncor.setText(cor + "/" + (answerList.size() - cor));
            labelProcent.setText(String.format("%.2f", 100.0 / answerList.size() * cor) + "%");
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
