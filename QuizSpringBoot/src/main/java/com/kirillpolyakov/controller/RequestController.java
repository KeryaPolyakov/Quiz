package com.kirillpolyakov.controller;

import com.kirillpolyakov.dto.ResponseResult;
import com.kirillpolyakov.model.Request;
import com.kirillpolyakov.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("request")
public class RequestController {

    private RequestService requestStatisticService;

    @Autowired
    public void setRequestStatisticService(RequestService requestStatisticService) {
        this.requestStatisticService = requestStatisticService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<List<Request>>> getAllBySimpleUserId(@PathVariable long id) {
        return new ResponseEntity<>(
                new ResponseResult<>(null, this.requestStatisticService.getAllByUserId(id)), HttpStatus.OK);
    }
}
