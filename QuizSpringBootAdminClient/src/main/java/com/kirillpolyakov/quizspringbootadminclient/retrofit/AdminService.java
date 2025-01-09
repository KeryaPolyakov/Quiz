package com.kirillpolyakov.quizspringbootadminclient.retrofit;

import com.kirillpolyakov.quizspringbootadminclient.model.Admin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AdminService {

    @POST("admin")
    Call<Admin> post (@Body Admin admin);

}
