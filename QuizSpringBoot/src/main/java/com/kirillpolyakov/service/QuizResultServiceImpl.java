package com.kirillpolyakov.service;

import com.kirillpolyakov.model.QuizResult;
import com.kirillpolyakov.model.Request;
import com.kirillpolyakov.model.SimpleUser;
import com.kirillpolyakov.repository.QuizRepository;
import com.kirillpolyakov.util.Constants;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizResultServiceImpl implements QuizResultService {

    private RestTemplate restTemplate = new RestTemplate();

    private QuizRepository quizRepository;

    private ResultService resultService;

    private SimpleUserService simpleUserService;

    private RequestService requestService;


    @Autowired
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }

    @Autowired
    public void setResultService(ResultService resultService) {
        this.resultService = resultService;
    }

    @Autowired
    public void setQuizRepository(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Autowired
    public void setSimpleUserService(SimpleUserService simpleUserService) {
        this.simpleUserService = simpleUserService;
    }

    @Override
    public QuizResult get(int amount, int category, String difficulty, long userId) {
        checkAPIRequest(amount, category, difficulty);
        SimpleUser simpleUser = this.simpleUserService.get(userId);
        QuizResult quizResult = restTemplate.exchange(
                MessageFormat.format(Constants.URL, amount, category, difficulty),
                HttpMethod.GET, null, new ParameterizedTypeReference<QuizResult>() {
                }).getBody();
        quizResult.setSimpleUser(simpleUser);
        this.add(quizResult);
        Request request = new Request(amount, category, difficulty, quizResult, simpleUser);
        this.requestService.add(request);
        quizResult.getResults().forEach(x -> {
            x.setQuizResult(quizResult);
            x.setQuestion(StringEscapeUtils.unescapeHtml4(x.getQuestion()));
            x.setCorrectAnswer(StringEscapeUtils.unescapeHtml4(x.getCorrectAnswer()));
            x.setIncorrectAnswers(x.getIncorrectAnswers().stream().map(y -> StringEscapeUtils.unescapeHtml4(y))
                    .collect(Collectors.toList()));
            this.resultService.add(x);
        });
        return new QuizResult(quizResult.getId(), quizResult.getResponseCode(), quizResult.getResults(),
                simpleUser, request);
    }

    @Override
    public void add(QuizResult quizResult) {
        this.quizRepository.save(quizResult);
    }

    @Override
    public QuizResult get(long id) {
        return this.quizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("QuizResult doesn't exist"));
    }

    @Override
    public QuizResult update(QuizResult quizResult) {
        QuizResult old = this.get(quizResult.getId());
        old.setResponseCode(quizResult.getResponseCode());
        old.setRequest(quizResult.getRequest());
        old.setSimpleUser(quizResult.getSimpleUser());
        old.setResults(quizResult.getResults());
        this.quizRepository.save(old);
        return old;
    }

    private void checkAPIRequest(int amount, int category, String difficulty) {
        if (amount > 10 || amount < 1) {
            throw new IllegalArgumentException("Amount of question should be between 1 and 10");
        } else if (!List.of(15, 17, 18).contains(category)) {
            throw new IllegalArgumentException("There isn't such name of category");
        } else if (!List.of("easy", "medium", "hard").contains(difficulty)) {
            throw new IllegalArgumentException("There isn't such difficulty");
        }
    }

}
