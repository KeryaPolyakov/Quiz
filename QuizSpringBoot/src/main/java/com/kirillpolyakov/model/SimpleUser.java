package com.kirillpolyakov.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "simple_users")
@Data
public class SimpleUser extends User{

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simpleUser")
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    private List<QuizResult> quizResults;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simpleUser")
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    private List<Request> requests;

}
