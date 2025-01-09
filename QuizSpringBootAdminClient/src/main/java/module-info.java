module com.kirillpolyakov.quizspringbootadminclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires static lombok;
    requires java.prefs;
    requires okhttp3;
    requires retrofit2;
    requires retrofit2.converter.jackson;


    opens com.kirillpolyakov.quizspringbootadminclient to javafx.fxml;
    exports com.kirillpolyakov.quizspringbootadminclient;
    exports com.kirillpolyakov.quizspringbootadminclient.controller to javafx.fxml;
    opens com.kirillpolyakov.quizspringbootadminclient.controller to javafx.fxml;
    exports com.kirillpolyakov.quizspringbootadminclient.model to com.fasterxml.jackson.databind;
    exports com.kirillpolyakov.quizspringbootadminclient.dto to com.fasterxml.jackson.databind;
    opens com.kirillpolyakov.quizspringbootadminclient.model to javafx.base;
}