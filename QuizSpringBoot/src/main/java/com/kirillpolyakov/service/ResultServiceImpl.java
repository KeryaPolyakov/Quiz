package com.kirillpolyakov.service;

import com.kirillpolyakov.model.Result;
import com.kirillpolyakov.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements ResultService{

    private ResultRepository resultRepository;

    private QuizResultService quizResultService;

    @Autowired
    public void setQuizResultService(QuizResultService quizResultService) {
        this.quizResultService = quizResultService;
    }

    @Autowired
    public void setResultRepository(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }


    @Override
    public Result get(long id) {
        return this.resultRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Result doesn't exist"));
    }

    @Override
    public void add(Result result) {
        this.resultRepository.save(result);
    }
}
