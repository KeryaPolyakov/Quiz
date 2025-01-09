package com.kirillpolyakov.service;

import com.kirillpolyakov.model.Result;

public interface ResultService {

    Result get(long id);

    void add(Result result);

}
