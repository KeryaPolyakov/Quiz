package com.kirillpolyakov.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table
public class Request {
    @Id
    private long id;

    @NonNull
    private int amount;

    @NonNull
    private int category;

    @NonNull
    private String difficulty;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime localDateTime = LocalDateTime.now();

    @NonNull
    @ToString.Exclude
    @OneToOne
    @MapsId
    @JoinColumn(name = "quiz_result_id")
    private QuizResult quizResult;

    @NonNull
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "simple_user_id")
    private SimpleUser simpleUser;

}

