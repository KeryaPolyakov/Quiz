package com.kirillpolyakov.quizspringbootclient.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Constants {

    public static final String URL = "http://localhost:8080/";

    public static final String PREFERENCE_KEY_ID = "id";

    public static final String PREFERENCE_KEY_USERNAME = "username";

    public static final String PREFERENCE_KEY_PASSWORD = "password";

    public static final String PREFERENCE_KEY_CHECKBOX = "check";

    public static final List<String> allDifficulties = List.of("Easy", "Medium", "Hard");

    public static final List<String> categories = List.of("Entertainment", "Science &amp; Nature", "Science: Computers");

    public static final Map<String, Integer> numberCategory = Map.of("Entertainment", 15, "Science &amp; Nature", 17,
            "Science: Computers", 18);

}
