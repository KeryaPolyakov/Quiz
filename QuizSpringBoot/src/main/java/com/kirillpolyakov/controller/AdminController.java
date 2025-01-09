package com.kirillpolyakov.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirillpolyakov.dto.ResponseResult;
import com.kirillpolyakov.model.Admin;
import com.kirillpolyakov.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.StringJoiner;

@RestController
@RequestMapping("admin")
public class AdminController {

    private AdminService adminService;

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public void add(HttpServletRequest req, HttpServletResponse resp, @Valid @RequestBody Admin admin) {
        try {
            req.setCharacterEncoding("utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/json");
            resp.setStatus(200);
            this.objectMapper.writerFor(new TypeReference<Admin>() {
            }).writeValue(resp.getWriter(), this.adminService.add(admin));
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
        resp.setStatus(400);
        resp.setContentType("application/json;charset=utf-8");
        StringJoiner stringJoiner = new StringJoiner(";");
        ex.getBindingResult().getAllErrors().forEach(x -> stringJoiner.add(x.getDefaultMessage()));
        try {
            this.objectMapper.writeValue(resp.getWriter(), new ResponseResult<>(stringJoiner.toString(), null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
