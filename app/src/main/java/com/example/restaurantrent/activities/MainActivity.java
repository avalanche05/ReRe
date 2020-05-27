package com.example.restaurantrent.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.restaurantrent.Rent;
import com.example.restaurantrent.constant.ActConst;
import com.example.restaurantrent.R;
import com.example.restaurantrent.Restaurant;
import com.example.restaurantrent.services.HttpService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static long idUser;

    public static ArrayList<Restaurant> restaurants = new ArrayList<>();

    public static ArrayList<Rent> rents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Intent intent = new Intent(MainActivity.this, HttpService.class);
        intent.putExtra("act", ActConst.GET_ALL_RESTAURANTS_ACT);
        startService(intent);
        Intent i = new Intent(MainActivity.this, HttpService.class);
        i.putExtra("act", ActConst.GET_RENTS_ACT);
        i.putExtra("idUser", MainActivity.idUser);
        startService(i);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
