package com.kirillpolyakov.quizspringbootclient.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(value = {@JsonSubTypes.Type(value = SimpleUser.class, name = "simpleUser"),
        @JsonSubTypes.Type(value = Admin.class, name = "admin")})
public class User {

    private long id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String name;

    @NonNull
    private String surname;


}
