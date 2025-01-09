package com.kirillpolyakov.service;

import com.kirillpolyakov.model.Answer;
import com.kirillpolyakov.model.Result;
import com.kirillpolyakov.repository.AnswerRepository;
import com.kirillpolyakov.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService{

    private AnswerRepository answerRepository;

    private ResultService resultService;

    @Autowired
    public void setResultService(ResultService resultService) {
        this.resultService = resultService;
    }

    @Autowired
    public void setAnswerRepository(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public void add(Answer answer, long resultId) {
        Result result = this.resultService.get(resultId);
        Util.checkPermission(result.getQuizResult().getSimpleUser().getId(), "User can add answer only for himself");
        answer.setResult(result);
        try {
            this.answerRepository.save(answer);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Answer is already exist");
        }
    }

    @Override
    public List<Answer> getAllByUserId(long id) {
        return this.answerRepository.findAllByResultQuizResultSimpleUserId(id);
    }

    @Override
    public List<Answer> getAllByQuizResultId(long id) {
        return this.answerRepository.findAllByResultQuizResultId(id);
    }

}
