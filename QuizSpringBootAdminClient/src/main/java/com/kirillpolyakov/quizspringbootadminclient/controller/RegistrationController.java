package com.kirillpolyakov.quizspringbootadminclient.controller;

import com.kirillpolyakov.quizspringbootadminclient.App;
import com.kirillpolyakov.quizspringbootadminclient.model.Admin;
import com.kirillpolyakov.quizspringbootadminclient.model.SimpleUser;
import com.kirillpolyakov.quizspringbootadminclient.retrofit.AdminRepository;
import com.kirillpolyakov.quizspringbootadminclient.retrofit.SimpleUserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class RegistrationController {
    @FXML
    public TextField textFieldPassword;
    @FXML
    public TextField textFieldUserName;
    @FXML
    public TextField textFieldName;
    @FXML
    public TextField textFieldSurname;


    public void buttonAddAdmin(ActionEvent actionEvent) {
        String userName = textFieldUserName.getText();
        String password = textFieldPassword.getText();
        String name = textFieldName.getText();
        String surname = textFieldSurname.getText();
        if (userName.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty()) {
            App.showInfo("Error", "Fields can't be empty", Alert.AlertType.ERROR);
        } else {
            try {
                Preferences preferences = Preferences.userNodeForPackage(App.class);
                new AdminRepository(preferences.get("username", ""),
                        preferences.get("password", "")).post(new Admin(userName, password, name, surname));
                App.showInfo("Info", "User is successfully added", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) textFieldName.getScene().getWindow();
                stage.close();
            } catch (IllegalArgumentException | IOException e) {
                App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
}
