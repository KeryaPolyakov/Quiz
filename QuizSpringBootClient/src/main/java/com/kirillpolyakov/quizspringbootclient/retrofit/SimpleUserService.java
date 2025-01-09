package com.kirillpolyakov.quizspringbootclient.retrofit;

import com.kirillpolyakov.quizspringbootclient.model.SimpleUser;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface SimpleUserService {

    @POST("simpleUser")
    Call<SimpleUser> post (@Body SimpleUser admin);

}
