package com.kirillpolyakov.quizspringbootadminclient.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUser extends User{

    private List<QuizResult> quizResults = new ArrayList<>();

    private List<Request> requests = new ArrayList<>();

    public SimpleUser(@NonNull String username, @NonNull String password, @NonNull String name, @NonNull String surname) {
        super(username, password, name, surname);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
