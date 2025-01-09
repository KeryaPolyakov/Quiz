module com.kirillpolyakov.quizspringbootclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.prefs;
    requires static lombok;
    requires okhttp3;
    requires retrofit2;
    requires retrofit2.converter.jackson;


    opens com.kirillpolyakov.quizspringbootclient to javafx.fxml;
    exports com.kirillpolyakov.quizspringbootclient;
    exports com.kirillpolyakov.quizspringbootclient.controller to javafx.fxml;
    opens com.kirillpolyakov.quizspringbootclient.controller to javafx.fxml;
    exports com.kirillpolyakov.quizspringbootclient.dto to com.fasterxml.jackson.databind;
    exports com.kirillpolyakov.quizspringbootclient.model to com.fasterxml.jackson.databind;
}