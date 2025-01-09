package com.kirillpolyakov.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(value = {@JsonSubTypes.Type(value = SimpleUser.class, name = "simpleUser"),
        @JsonSubTypes.Type(value = Admin.class, name = "admin")})
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username should consist only of letters or digits")
    @Size(min = 3, message = "Username length should be more than 3 symbols")
    private String username;

    @NonNull
    @Column
    @NotBlank
    @Size(min = 3, message = "Password length should be more then 3 symbols")
    private String password;

    @NonNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name should consist only of letters")
    private String name;

    @NonNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Surname should consist only of letters")
    private String surname;




}
