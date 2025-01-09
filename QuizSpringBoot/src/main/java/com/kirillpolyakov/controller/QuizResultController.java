package com.kirillpolyakov.controller;

import com.kirillpolyakov.dto.ResponseResult;
import com.kirillpolyakov.model.QuizResult;
import com.kirillpolyakov.service.QuizResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quiz_result")
public class QuizResultController {

    private QuizResultService quizResultService;

    @Autowired
    public void setQuizResultService(QuizResultService quizResultService) {
        this.quizResultService = quizResultService;
    }

    @PreAuthorize("authentication.principal.id == #userId")
    @GetMapping
    public ResponseEntity<ResponseResult<QuizResult>> get(@RequestParam int amount, @RequestParam int category,
                                                          @RequestParam String difficulty, @RequestParam long userId) {
        try {
            return new ResponseEntity<>(
                    new ResponseResult<>(null, this.quizResultService.get(amount,category,difficulty, userId)),
                    HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

}
