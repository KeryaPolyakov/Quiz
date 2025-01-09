package com.kirillpolyakov.quizspringbootclient.controller;

import com.kirillpolyakov.quizspringbootclient.App;
import com.kirillpolyakov.quizspringbootclient.model.QuizResult;
import com.kirillpolyakov.quizspringbootclient.retrofit.QuizResultRepository;
import com.kirillpolyakov.quizspringbootclient.util.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;


public class LoadingFormController {

    @FXML
    public TextField textFieldNumberOfQuestions;
    @FXML
    public ComboBox<String> comboBoxSelectCategory;

    @FXML
    public ComboBox<String> comboBoxSelectDifficulty;

    @FXML
    public void initialize() {
        this.comboBoxSelectDifficulty.getItems()
                .addAll(Constants.allDifficulties);
        this.comboBoxSelectCategory.getItems().addAll(Constants.numberCategory.keySet());

    }

    public void ButtonStart(ActionEvent actionEvent) {
        try {
            Preferences preferences = Preferences.userNodeForPackage(App.class);
            System.out.println(preferences.getBoolean("check", false));
            int amount = Integer.parseInt(this.textFieldNumberOfQuestions.getText());
            String difficulty = this.comboBoxSelectDifficulty.getSelectionModel().getSelectedItem().toLowerCase();
            int category = Constants.numberCategory.get(this.comboBoxSelectCategory.getSelectionModel().getSelectedItem());
            QuizResult quizResult = new QuizResultRepository(preferences.get("username", ""),
                    preferences.get("password", "")).get(amount, category, difficulty,
                    preferences.getLong("id", -1));
            App.openWindow("gameForm.fxml", "Game", quizResult);
        } catch (NullPointerException | NumberFormatException e) {
            App.showInfo("Error", "Fields can't be empty or number of question should be digits",
                    Alert.AlertType.ERROR);
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
            App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void ButtonBack(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) comboBoxSelectCategory.getScene().getWindow();
            stage.close();
            App.openWindow("main.fxml", "Main", null);
        } catch (IOException e) {
            App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
