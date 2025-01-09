package com.kirillpolyakov.service;

import com.kirillpolyakov.model.Answer;

import java.util.List;

public interface AnswerService {

    void add(Answer answer, long resultId);

    List<Answer> getAllByUserId(long id);

    List<Answer> getAllByQuizResultId(long id);
}
