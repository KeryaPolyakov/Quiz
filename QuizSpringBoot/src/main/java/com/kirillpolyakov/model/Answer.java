package com.kirillpolyakov.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    private long id;

    @NonNull
    private String answer;

    @JsonFormat(pattern = "dd.MM.yyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime localDateTime = LocalDateTime.now();

    private boolean isCorrect;

    @OneToOne
    @MapsId
    @JoinColumn(name = "result_id")
    private Result result;


}
