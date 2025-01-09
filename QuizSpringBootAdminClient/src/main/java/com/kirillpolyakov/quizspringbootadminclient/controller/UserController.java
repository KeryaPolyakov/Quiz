package com.kirillpolyakov.quizspringbootadminclient.controller;

import com.kirillpolyakov.quizspringbootadminclient.App;
import com.kirillpolyakov.quizspringbootadminclient.model.Request;
import com.kirillpolyakov.quizspringbootadminclient.model.SimpleUser;
import com.kirillpolyakov.quizspringbootadminclient.retrofit.RequestRepository;
import com.kirillpolyakov.quizspringbootadminclient.util.Constants;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.prefs.Preferences;

public class UserController implements ControllerData<SimpleUser> {
    @FXML
    public TableView<Request> tableViewRequest;
    @FXML
    public TextField textFieldUsername;
    @FXML
    public TextField textFieldName;
    @FXML
    public TextField textFieldSurname;
    @FXML
    public TextField textFieldRequestCount;

    private RequestRepository requestRepository;

    private Preferences preferences;

    private SimpleUser simpleUser;

    @Override
    public void initData(SimpleUser data) {
        simpleUser = data;
        preferences = Preferences.userNodeForPackage(App.class);
        textFieldUsername.setText(simpleUser.getUsername());
        textFieldName.setText(simpleUser.getName());
        textFieldSurname.setText(simpleUser.getSurname());
        textFieldUsername.setEditable(false);
        textFieldName.setEditable(false);
        textFieldSurname.setEditable(false);
        try {
            requestRepository = new RequestRepository(preferences.get("username", ""),
                    preferences.get("password", ""));
            TableColumn<Request, String> id = new TableColumn<>("Id");
            TableColumn<Request, String> amount = new TableColumn<>("Amount");
            TableColumn<Request, String> category = new TableColumn<>("Category");
            TableColumn<Request, String> difficulty = new TableColumn<>("Difficulty");
            TableColumn<Request, String> dateTime = new TableColumn<>("DateTime");
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            category.setCellValueFactory(requestStringCellDataFeatures ->
                    new SimpleStringProperty(
                            Constants.numberCategory.get(requestStringCellDataFeatures.getValue().getCategory())));
            difficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            dateTime.setCellValueFactory(requestStringCellDataFeatures ->
                    new SimpleStringProperty(
                            dateTimeFormatter.format(requestStringCellDataFeatures.getValue().getLocalDateTime())));
            tableViewRequest.getColumns().addAll(id, amount, category, difficulty, dateTime);
            List<Request> requestList = requestRepository.getAllByUser(simpleUser.getId());
            tableViewRequest.setItems(FXCollections.observableList(requestList));
            textFieldRequestCount.setText(String.valueOf(requestList.size()));
            textFieldRequestCount.setEditable(false);
            tableViewRequest.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2) {
                    Request request = tableViewRequest.getSelectionModel().getSelectedItem();
                    try {
                        if (request != null) {
                            App.openWindowAndWaitModal("quizResult.fxml", "QuizResult", request);
                            requestRepository.getAllByUser(simpleUser.getId());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void buttonAllAnswers(ActionEvent actionEvent) {
        try {
            App.openWindowAndWaitModal("answers.fxml", "Answers", simpleUser);
            requestRepository.getAllByUser(simpleUser.getId());
        } catch (IOException e) {
            e.printStackTrace();
            App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void buttonBack(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) textFieldRequestCount.getScene().getWindow();
            stage.close();
            App.openWindow("main.fxml", "Users", null);
        } catch (IOException e) {
            e.printStackTrace();
            App.showInfo("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
