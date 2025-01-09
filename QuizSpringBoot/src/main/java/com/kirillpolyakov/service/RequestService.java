package com.kirillpolyakov.service;

import com.kirillpolyakov.model.Request;

import java.util.List;

public interface RequestService {

    void add(Request request);

    List<Request> getAllByUserId(long id);
}
