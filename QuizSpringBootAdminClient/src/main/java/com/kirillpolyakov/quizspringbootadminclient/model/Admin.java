package com.kirillpolyakov.quizspringbootadminclient.model;

import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Admin extends User{

    public Admin(@NonNull String username, @NonNull String password, @NonNull String name, @NonNull String surname) {
        super(username, password, name, surname);
    }
}
