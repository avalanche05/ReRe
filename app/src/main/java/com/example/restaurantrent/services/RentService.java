package com.example.restaurantrent.services;

import com.example.restaurantrent.Rent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

// сервис для работы с заказами на сервере
public interface RentService {

    @POST("rent/user/get")
    Call<ArrayList<Rent>> getUserRent(@Query("idUser") Long idUser);

    @POST("rent/add")
    Call<String> rentAdd(@Body Rent rent);

    @POST("rent/delete")
    Call<String> rentDelete(@Query("id") Long id);
}
