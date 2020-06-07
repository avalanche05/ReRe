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
import com.example.restaurantrent.Server;
import com.example.restaurantrent.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

// activity для отображения фрагментов
public class MainActivity extends AppCompatActivity {

    // поле пользователя
    public static User user = new User();
    // массив всех ресторанов
    public static ArrayList<Restaurant> restaurants = new ArrayList<>();
    // массив заказов, сделанных пользователем
    public static ArrayList<Rent> rents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // берём пользователя из класса Server
        user = (User) getIntent().getSerializableExtra("user");

        Server.isConfirm(MainActivity.user.getId());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
