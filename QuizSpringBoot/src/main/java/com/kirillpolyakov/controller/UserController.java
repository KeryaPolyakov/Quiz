package com.kirillpolyakov.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirillpolyakov.dto.ResponseResult;
import com.kirillpolyakov.model.User;
import com.kirillpolyakov.model.UserDetailsImpl;
import com.kirillpolyakov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("user")
public class UserController {

    public UserService userService;

    public ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("authentication")
    public void authenticate(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) {
        try {
            req.setCharacterEncoding("utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/json;charset=utf-8");
            if (authentication.isAuthenticated()) {
                long id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
                try {
                    this.objectMapper.writerFor(new TypeReference<User>() {
                    }).writeValue(resp.getWriter(), this.userService.get(id));
                } catch (IllegalArgumentException e) {
                    resp.setStatus(400);
                    this.objectMapper.writeValue(resp.getWriter(), new ResponseResult<>(e.getMessage(), null));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/{id}")
    public void get(HttpServletRequest req, HttpServletResponse resp, @PathVariable long id) {
        try {
            req.setCharacterEncoding("utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/json;charset=utf-8");
            resp.setStatus(200);
            try {
                this.objectMapper.writerFor(new TypeReference<User>() {
                }).writeValue(resp.getWriter(), this.userService.get(id));
            } catch (IllegalArgumentException e) {
                resp.setStatus(400);
                this.objectMapper.writeValue(resp.getWriter(), new ResponseResult<>(e.getMessage(), null));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentExceptions (HttpServletResponse resp, IllegalArgumentException ex) {
        try {
            resp.setStatus(400);
            resp.setContentType("application/json;charset=utf-8");
            this.objectMapper.writeValue(resp.getWriter(), new ResponseResult<>(null, ex.getMessage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
