package com.example.restaurantrent.activities.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.restaurantrent.Helper;
import com.example.restaurantrent.R;
import com.example.restaurantrent.Server;
import com.example.restaurantrent.activities.LoginActivity;
import com.example.restaurantrent.activities.MainActivity;

// фрагмент информации о пользователе
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView userInfo = root.findViewById(R.id.userInformationTextView);
        final Button outAccountButton = root.findViewById(R.id.outButton);
        final TextView confirmTextView = root.findViewById(R.id.confirmTextView);
        homeViewModel.getText().observe(this, new Observer<String>() {

            @Override
            public void onChanged(@Nullable String s) {
                // отпраляем запрос на сервер о состоянии электронной почты на сервер
                Server.isConfirm(MainActivity.user.getId());
                // проверяем подтверждена ли почта
                if (MainActivity.user.getAuth()) {
                    // ставим текст о том, что почта подтверждена
                    confirmTextView.setText(R.string.email_confirm);
                    confirmTextView.setTextColor(Color.GREEN);
                } else {
                    // ставим текст о том, что почта подтверждена
                    confirmTextView.setText(R.string.email_not_confirm);
                    confirmTextView.setTextColor(Color.RED);
                }

                // отслеживаем нажатие кнопки выхода из аккаунта
                outAccountButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // удаляем пользователя из SharedPreferences
                        SharedPreferences preferences = getActivity().getSharedPreferences(Helper.APP_PREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove(Helper.APP_PREFERENCES_EMAIL);
                        editor.remove(Helper.APP_PREFERENCES_PASSWORD);
                        editor.apply();

                        // переходим в LoginActivity
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);

                        // закрываем текущее activity
                        getActivity().finish();
                    }
                });
                userInfo.setText(MainActivity.user.getEmail());
            }
        });
        return root;
    }
}