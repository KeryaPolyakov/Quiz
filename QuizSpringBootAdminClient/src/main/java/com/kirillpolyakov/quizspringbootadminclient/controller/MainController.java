package com.kirillpolyakov.quizspringbootadminclient.controller;

import com.kirillpolyakov.quizspringbootadminclient.App;
import com.kirillpolyakov.quizspringbootadminclient.model.SimpleUser;
import com.kirillpolyakov.quizspringbootadminclient.retrofit.SimpleUserRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class MainController {
    public ListView<SimpleUser> listViewUsers;

    private Preferences preferences;

    public void initialize() {
        preferences = Preferences.userNodeForPackage(App.class);
        SimpleUserRepository simpleUserRepository = new SimpleUserRepository(preferences.get("username", ""),
                preferences.get("password", ""));
        try {
            listViewUsers.setItems(FXCollections.observableList(simpleUserRepository.getAll()));
            listViewUsers.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2) {
                    SimpleUser simpleUser = listViewUsers.getSelectionModel().getSelectedItem();
                    if (simpleUser != null) {
                        try {
                            App.openWindow("user.fxml", simpleUser.getUsername(), simpleUser);
                            Stage stage = (Stage) listViewUsers.getScene().getWindow();
                            stage.close();
                        } catch (IOException e) {
                            App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void buttonExit(ActionEvent actionEvent) {
        try {
            preferences.remove("username");
            preferences.remove("password");
            preferences.remove("id");
            Stage stage = (Stage) listViewUsers.getScene().getWindow();
            stage.close();
            App.openWindow("authorisation.fxml", "Authorisation", null);
        } catch (IOException e) {
            e.printStackTrace();
            App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void buttonAddAdmin(ActionEvent actionEvent) {
        try {
            App.openWindowAndWaitModal("registration.fxml", "Registration", null);
        } catch (IOException e) {
            e.printStackTrace();
            App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
