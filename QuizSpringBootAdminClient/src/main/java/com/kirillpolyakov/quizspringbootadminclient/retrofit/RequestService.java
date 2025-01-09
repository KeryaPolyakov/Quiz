package com.kirillpolyakov.quizspringbootadminclient.retrofit;

import com.kirillpolyakov.quizspringbootadminclient.dto.ResponseResult;
import com.kirillpolyakov.quizspringbootadminclient.model.QuizResult;
import com.kirillpolyakov.quizspringbootadminclient.model.Request;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;


public interface RequestService {

    @GET("request/{id}")
    Call<ResponseResult<List<Request>>> getAllByUser(@Path("id") long userId);


}
