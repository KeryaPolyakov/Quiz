package com.kirillpolyakov.quizspringbootclient.controller;

import com.kirillpolyakov.quizspringbootclient.App;
import com.kirillpolyakov.quizspringbootclient.util.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class MainController {

    @FXML
    public CheckBox checkBoxAnswer;


    public void initialize() {
        Preferences preferences = Preferences.userNodeForPackage(App.class);
        if (preferences.getBoolean("check", false)) {
            checkBoxAnswer.setSelected(true);
        }
        checkBoxAnswer.setOnAction(x -> {
            if (checkBoxAnswer.isSelected()) {
                preferences.putBoolean(Constants.PREFERENCE_KEY_CHECKBOX, checkBoxAnswer.isSelected());
            } else {
                preferences.remove("check");
            }
        });
    }
    public void buttonStart(ActionEvent actionEvent) {

        try {
            Stage stage = (Stage) this.checkBoxAnswer.getScene().getWindow();
            stage.close();
            App.openWindow("loadingForm.fxml", "Quiz", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void buttonExit(ActionEvent actionEvent) {
        try {
            Preferences preferences = Preferences.userNodeForPackage(App.class);
            preferences.remove("id");
            preferences.remove("username");
            preferences.remove("password");
            preferences.remove("check");
            App.openWindow("authorisation.fxml", "Authorisation", null);
            Stage stage = (Stage) checkBoxAnswer.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
