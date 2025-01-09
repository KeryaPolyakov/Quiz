package com.kirillpolyakov.quizspringbootadminclient.retrofit;

import com.kirillpolyakov.quizspringbootadminclient.dto.ResponseResult;
import com.kirillpolyakov.quizspringbootadminclient.model.QuizResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface QuizResultService {

    @GET("quiz_result")
    Call<ResponseResult<QuizResult>> get(@Query("amount") int amount, @Query("category") int category,
                                         @Query("difficulty") String difficulty, @Query("userId") long userId);


}
