package com.kirillpolyakov.quizspringbootclient.retrofit;

import com.kirillpolyakov.quizspringbootclient.dto.ResponseResult;
import com.kirillpolyakov.quizspringbootclient.model.Answer;
import com.kirillpolyakov.quizspringbootclient.model.QuizResult;
import retrofit2.Call;
import retrofit2.http.*;


public interface AnswerService {

    @POST("answer/{id}")
    Call<ResponseResult<Answer>> add(@Body Answer answer,  @Path("id") long resultId);


}
