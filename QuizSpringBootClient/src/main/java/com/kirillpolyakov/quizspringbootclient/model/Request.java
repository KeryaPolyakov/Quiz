package com.kirillpolyakov.quizspringbootclient.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Request {
    private long id;

    @NonNull
    private int amount;

    @NonNull
    private int category;

    @NonNull
    private String difficulty;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime localDateTime = LocalDateTime.now();

    @ToString.Exclude
    @JsonIgnore
    private QuizResult quizResult;

    private SimpleUser simpleUser;

}

