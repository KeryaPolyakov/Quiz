package com.kirillpolyakov.quizspringbootadminclient.retrofit;

import com.kirillpolyakov.quizspringbootadminclient.model.User;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("user/{id}")
    Call<User> get(@Path("id") long id);

    @GET("user/authentication")
    Call<User>  authenticate();

    @DELETE("user/{id}")
    Call<User> delete(@Path("id") long id);



}
