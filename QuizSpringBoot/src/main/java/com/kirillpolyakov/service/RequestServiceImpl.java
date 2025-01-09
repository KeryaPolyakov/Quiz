package com.kirillpolyakov.service;

import com.kirillpolyakov.model.Request;
import com.kirillpolyakov.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private RequestRepository requestStatisticRepository;

    private QuizResultService quizResultService;

    private SimpleUserService simpleUserService;

    @Autowired
    public void setSimpleUserService(SimpleUserService simpleUserService) {
        this.simpleUserService = simpleUserService;
    }

    @Autowired
    public void setQuizResultService(QuizResultService quizResultService) {
        this.quizResultService = quizResultService;
    }

    @Autowired
    public void setRequestStatisticRepository(RequestRepository requestStatisticRepository) {
        this.requestStatisticRepository = requestStatisticRepository;
    }

    @Override
    public void add(Request request) {
        this.requestStatisticRepository.save(request);
    }

    @Override
    public List<Request> getAllByUserId(long id) {
        return this.requestStatisticRepository.findAllByQuizResultSimpleUserId(id);
    }
}
