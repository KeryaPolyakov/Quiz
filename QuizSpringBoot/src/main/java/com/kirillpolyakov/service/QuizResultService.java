package com.kirillpolyakov.service;

import com.kirillpolyakov.model.QuizResult;

public interface QuizResultService {


    QuizResult get(int amount, int category, String difficulty, long userId);

    void add(QuizResult quizResult);

    QuizResult get(long id);

    QuizResult update(QuizResult quizResult);

}
