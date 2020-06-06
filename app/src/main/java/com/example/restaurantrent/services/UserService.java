package com.example.restaurantrent.services;

import com.example.restaurantrent.User;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

// сервис для работы с пользователем на сервере
public interface UserService {

    @POST("user/login")
    Call<User> loginUser(@Query("email") String email, @Query("password") String password);

    @POST("user/signup")
    Call<User> signUpUser(@Query("email") String email, @Query("password") String password);

    @POST("user/confirm")
    Call<Boolean> isConfirm(@Query("id") Long id);

}
