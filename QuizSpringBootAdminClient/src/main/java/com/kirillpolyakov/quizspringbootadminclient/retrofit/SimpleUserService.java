package com.kirillpolyakov.quizspringbootadminclient.retrofit;

import com.kirillpolyakov.quizspringbootadminclient.model.SimpleUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface SimpleUserService {

    @POST("simpleUser")
    Call<SimpleUser> post (@Body SimpleUser admin);

    @GET("simpleUser/getAll")
    Call<List<SimpleUser>> getAll();
}
