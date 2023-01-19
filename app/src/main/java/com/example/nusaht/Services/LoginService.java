package com.example.nusaht.Services;
import com.example.nusaht.models.LoginModel;
import com.example.nusaht.models.LoginResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface LoginService {

    @FormUrlEncoded
    @POST("account/login/token/")
    Call<LoginResponse> loginUser(@Field("username") String username, @Field("password") String password);
}


