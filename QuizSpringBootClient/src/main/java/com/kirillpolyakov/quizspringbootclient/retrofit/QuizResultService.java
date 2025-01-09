package com.kirillpolyakov.quizspringbootclient.retrofit;

import com.kirillpolyakov.quizspringbootclient.dto.ResponseResult;
import com.kirillpolyakov.quizspringbootclient.model.QuizResult;
import retrofit2.Call;
import retrofit2.http.*;


public interface QuizResultService {

    @GET("quiz_result")
    Call<ResponseResult<QuizResult>> get(@Query("amount") int amount, @Query("category") int category,
                                         @Query("difficulty") String difficulty, @Query("userId") long userId);


}
