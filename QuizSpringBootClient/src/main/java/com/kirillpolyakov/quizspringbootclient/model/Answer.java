package com.kirillpolyakov.quizspringbootclient.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Answer {
    private long id;

    @NonNull
    private String answer;

    @JsonFormat(pattern = "dd.MM.yyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime localDateTime = LocalDateTime.now();

    @NonNull
    private boolean isCorrect;

    private Result result;


}
