package com.kirillpolyakov.controller;

import com.kirillpolyakov.dto.ResponseResult;
import com.kirillpolyakov.model.Answer;
import com.kirillpolyakov.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("answer")
public class AnswerController {

    private AnswerService answerService;

    @Autowired
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Answer>> add(@RequestBody Answer answer, @PathVariable long id) {
        try {
            this.answerService.add(answer, id);
            return new ResponseEntity<>(new ResponseResult<>(null, answer), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/getAllByUserId/{id}")
    public ResponseEntity<ResponseResult<List<Answer>>> getAllByUserId(@PathVariable long id) {
        return new ResponseEntity<>(new ResponseResult<>(null,
                this.answerService.getAllByUserId(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/getAllByQuizResultId/{id}")
    public ResponseEntity<ResponseResult<List<Answer>>> getAllByQuizResultId(@PathVariable long id) {
        return new ResponseEntity<>(new ResponseResult<>(null,
                this.answerService.getAllByQuizResultId(id)), HttpStatus.OK);
    }

}
