package com.kirillpolyakov.quizspringbootadminclient.retrofit;

import com.kirillpolyakov.quizspringbootadminclient.dto.ResponseResult;
import com.kirillpolyakov.quizspringbootadminclient.model.Answer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;


public interface AnswerService {

    @GET("answer/getAllByQuizResultId/{id}")
    Call<ResponseResult<List<Answer>>> getAllByQuizResult(@Path("id") long resultId);

    @GET("answer/getAllByUserId/{id}")
    Call<ResponseResult<List<Answer>>> getAllByUser(@Path("id") long userId);
}
