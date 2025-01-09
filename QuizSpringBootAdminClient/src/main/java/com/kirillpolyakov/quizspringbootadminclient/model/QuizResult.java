package com.kirillpolyakov.quizspringbootadminclient.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class QuizResult {
    private long id;

    @NonNull
    private int responseCode;

    private List<Result> results = new ArrayList<>();

    private SimpleUser simpleUser;

    private Request request;


}
