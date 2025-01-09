package com.kirillpolyakov.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "results")
public class Result {
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String type;

    @NonNull
    private String difficulty;

    @NonNull
    private String category;

    @NonNull
    private String question;

    @NonNull
    @JsonProperty("correct_answer")
    private String correctAnswer;

    @NonNull
    @JsonProperty("incorrect_answers")
    @ElementCollection
    private List<String> incorrectAnswers = new ArrayList<>();


    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "quiz_result_id")
    private QuizResult quizResult;

    public Result(@NonNull long id, @NonNull String type, @NonNull String difficulty, @NonNull String category,
                  @NonNull String question, @NonNull String correctAnswer, @NonNull List<String> incorrectAnswers) {
        this.id = id;
        this.type = type;
        this.difficulty = difficulty;
        this.category = category;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }
}
