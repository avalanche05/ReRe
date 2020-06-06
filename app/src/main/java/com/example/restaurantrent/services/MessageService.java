package com.example.restaurantrent.services;

import com.example.restaurantrent.Message;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

// сервис для работы с сообщениями на сервере
public interface MessageService {

    @POST("message/send")
    Call<Boolean> messageSend(@Body Message message);

    @POST("message/get")
    Call<ArrayList<Message>> messageGet(@Query("idRent") Long idRent);
}
