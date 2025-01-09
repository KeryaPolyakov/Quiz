package com.kirillpolyakov.repository;

import com.kirillpolyakov.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllByResultQuizResultSimpleUserId(long id);

    List<Answer> findAllByResultQuizResultId(long id);
}
