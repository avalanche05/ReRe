package com.example.restaurantrent.services;

import com.example.restaurantrent.Restaurant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

// сервис для работы с ресторанами на сервере
public interface RestaurantService {

    @GET("restaurant/all")
    Call<ArrayList<Restaurant>> getAllRestaurants();

}
