package com.kirillpolyakov.repository;

import com.kirillpolyakov.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<QuizResult, Long> {

    List<QuizResult> findAllBySimpleUserId(long id);
}
