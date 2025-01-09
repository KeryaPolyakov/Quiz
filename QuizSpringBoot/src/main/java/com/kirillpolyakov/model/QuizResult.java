package com.kirillpolyakov.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "quiz_results")
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private int responseCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quizResult")
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    private List<Result> results = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "simple_user_id", nullable = false)
    private SimpleUser simpleUser;

    @JsonIgnore
    @OneToOne
    @PrimaryKeyJoinColumn
    private Request request;

    public QuizResult(long id, @NonNull int responseCode, List<Result> results, SimpleUser simpleUser) {
        this.id = id;
        this.responseCode = responseCode;
        this.results = results;
        this.simpleUser = simpleUser;
    }

    public QuizResult(long id, List<Result> results) {
        this.id = id;
        this.results = results;
    }
}
