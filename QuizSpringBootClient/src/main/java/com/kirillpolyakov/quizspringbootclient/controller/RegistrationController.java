package com.kirillpolyakov.quizspringbootclient.controller;

import com.kirillpolyakov.quizspringbootclient.App;
import com.kirillpolyakov.quizspringbootclient.model.SimpleUser;
import com.kirillpolyakov.quizspringbootclient.retrofit.SimpleUserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.AttributedString;

public class RegistrationController {
    @FXML
    public Button buttonSignUp;
    @FXML
    public Button buttonSignIn;
    @FXML
    public TextField textFieldPassword;
    @FXML
    public TextField textFieldUserName;
    @FXML
    public TextField textFieldName;
    @FXML
    public TextField textFieldSurname;

    @FXML
    public void buttonSignUp(ActionEvent actionEvent) {
        String userName = textFieldUserName.getText();
        String password = textFieldPassword.getText();
        String name = textFieldName.getText();
        String surname = textFieldSurname.getText();
        if (userName.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty()) {
            App.showInfo("Error", "Fields can't be empty", Alert.AlertType.ERROR);
        } else {
            try {
                new SimpleUserRepository().post(new SimpleUser(userName, password, name, surname));
                App.showInfo("Info", "User is successfully added", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) buttonSignUp.getScene().getWindow();
                stage.close();
                App.openWindow("authorisation.fxml", "Authorisation", null);
            } catch (IllegalArgumentException | IOException e) {
                App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void buttonSignIn(ActionEvent actionEvent) {
        try {
            App.openWindow("authorisation.fxml", "Authorisation", null);
            Stage stage = (Stage) buttonSignIn.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
