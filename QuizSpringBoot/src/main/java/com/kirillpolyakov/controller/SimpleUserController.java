package com.kirillpolyakov.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirillpolyakov.dto.ResponseResult;
import com.kirillpolyakov.model.SimpleUser;
import com.kirillpolyakov.service.SimpleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

@RestController
@RequestMapping("simpleUser")
public class SimpleUserController {

    private SimpleUserService simpleUserService;

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setSimpleUserService(SimpleUserService simpleUserService) {
        this.simpleUserService = simpleUserService;
    }

    @PostMapping
    public void add(HttpServletRequest req, HttpServletResponse resp, @Valid @RequestBody SimpleUser simpleUser) {
        try {
            req.setCharacterEncoding("utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/json;charset=utf-8");
            resp.setStatus(200);
            this.objectMapper.writerFor(new TypeReference<SimpleUser>() {
            }).writeValue(resp.getWriter(), this.simpleUserService.add(simpleUser));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getAll")
    public void getAllSimpleUsers(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/json;charset=utf-8");
            resp.setStatus(200);
            this.objectMapper.writerFor(new TypeReference<List<SimpleUser>>() {
            }).writeValue(resp.getWriter(), this.simpleUserService.getAll());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentExceptions(HttpServletResponse resp, IllegalArgumentException ex) {
        try {
            resp.setStatus(400);
            this.objectMapper.writeValue(resp.getWriter(), new ResponseResult<>(ex.getMessage(), null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidExceptions(HttpServletResponse resp, MethodArgumentNotValidException ex) {
        StringJoiner stringJoiner = new StringJoiner(";");
        ex.getBindingResult().getAllErrors().forEach(x -> stringJoiner.add(x.getDefaultMessage()));
        resp.setStatus(400);
        resp.setContentType("application/json;charset=utf-8");
        try {
            this.objectMapper.writeValue(resp.getWriter(), new ResponseResult<>(stringJoiner.toString(), null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
