package com.example.restaurantrent.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.restaurantrent.R;
import com.example.restaurantrent.Rent;
import com.example.restaurantrent.Restaurant;
import com.example.restaurantrent.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

// Activity для отображения фрагментов
public class MainActivity extends AppCompatActivity {

    // Поле пользователя
    public static User user = new User();
    // Массив всех ресторанов
    public static ArrayList<Restaurant> restaurants = new ArrayList<>();
    // Массив заказов, сделанных пользователем
    public static ArrayList<Rent> rents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Берём пользователя из класса Server
        user = (User) getIntent().getSerializableExtra("user");

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
