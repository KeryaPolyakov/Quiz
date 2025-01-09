package com.kirillpolyakov.quizspringbootadminclient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kirillpolyakov.quizspringbootadminclient.App;
import com.kirillpolyakov.quizspringbootadminclient.model.User;
import com.kirillpolyakov.quizspringbootadminclient.retrofit.UserRepository;
import com.kirillpolyakov.quizspringbootadminclient.util.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class AuthorizationController {
    @FXML
    public Button buttonSignIn;

    @FXML
    public TextField textFieldLogin;

    @FXML
    public TextField textFieldPassword;

    @FXML
    public Button buttonSignUp;

    private final ObjectMapper mapper = new ObjectMapper();

    {
        mapper.registerModule(new JavaTimeModule());
    }

    @FXML
    public void buttonSignIn(ActionEvent actionEvent) {
        String username = textFieldLogin.getText();
        String password = textFieldPassword.getText();
        if (username.isEmpty() || password.isEmpty()) {
            App.showInfo("Error", "Все поля должны быть заполнены", Alert.AlertType.ERROR);
        } else {
            try {
                User user = new UserRepository(username, password).authenticate();
                if (!user.getClass().getSimpleName().equals("Admin")) {
                    App.showInfo("Error", "Admin doesn't exist", Alert.AlertType.ERROR);
                } else {
                    Preferences preferences = Preferences.userNodeForPackage(App.class);
                    long id = user.getId();
                    preferences.putLong(Constants.PREFERENCE_KEY_ID, id);
                    preferences.put(Constants.PREFERENCE_KEY_USERNAME, username);
                    preferences.put(Constants.PREFERENCE_KEY_PASSWORD, password);
                    Stage stage = (Stage) buttonSignIn.getScene().getWindow();
                    stage.close();
                    App.openWindow("main.fxml", "Main", null);
                }
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
                App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void buttonSignUp(ActionEvent actionEvent) {
        try {
            App.openWindow("registration.fxml", "Registration", null);
            Stage stage = (Stage) buttonSignUp.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
