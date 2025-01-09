package com.kirillpolyakov.quizspringbootadminclient.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kirillpolyakov.quizspringbootadminclient.dto.ResponseResult;
import com.kirillpolyakov.quizspringbootadminclient.model.QuizResult;
import com.kirillpolyakov.quizspringbootadminclient.security.BasicAuthInterceptor;
import com.kirillpolyakov.quizspringbootadminclient.util.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class QuizResultRepository {
    private final ObjectMapper objectMapper;

    private QuizResultService service;

    public QuizResultRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(QuizResultService.class);
    }

    public QuizResultRepository(String userName, String password) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(userName, password)).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(QuizResultService.class);
    }

    private <T> T getData(Response<ResponseResult<T>> execute) throws IOException {
        if (execute.code() != 200) {
            String message = objectMapper.readValue(execute.errorBody().string(),
                    new TypeReference<ResponseResult<T>>() {
                    }).getMessage();
            throw new IllegalArgumentException(message);
        }
        return execute.body().getData();
    }


    public QuizResult get(int amount, int category, String difficulty, long userId) throws IOException {
        Response<ResponseResult<QuizResult>> execute = service.get(amount, category, difficulty, userId).execute();
        return getData(execute);
    }

}