package com.example.restaurantrent.services;

import com.example.restaurantrent.Board;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

// сервис для работы со столами на сервере
public interface TableService {
    @POST("table/get")
    Call<ArrayList<Board>> tableGet(@Query("idRestaurant") Long idRestaurant);
}
