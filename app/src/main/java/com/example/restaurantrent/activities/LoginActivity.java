package com.example.restaurantrent.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantrent.Helper;
import com.example.restaurantrent.R;
import com.example.restaurantrent.Server;

import java.io.IOException;

// activity для входа в приложение
public class LoginActivity extends AppCompatActivity {

    private TextView signUp;
    private EditText loginEmail;
    private EditText loginPassword;

    // поле для закрытия этого activity из другого класса
    public static Activity loginActivityThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginActivityThis = this;

        loginEmail = findViewById(R.id.emailEditText);
        loginPassword = findViewById(R.id.passwordEditText);
        signUp = findViewById(R.id.registrationTextView);

        // проверяем, не входил ли пользователь ранее
        SharedPreferences preferences = getSharedPreferences(Helper.APP_PREFERENCES, Context.MODE_PRIVATE);
        if (preferences.contains(Helper.APP_PREFERENCES_EMAIL) && preferences.contains(Helper.APP_PREFERENCES_PASSWORD)) {
            String email = preferences.getString(Helper.APP_PREFERENCES_EMAIL, "");
            String password = preferences.getString(Helper.APP_PREFERENCES_PASSWORD, "");
            // отправляем запрос входа на сервер
            // loginUser принимает контекст, для отправки Toast и создания Intent, ProgressBar, который нужно показывать при загрузке и окна, которые нужно сделать невидимыми во время обращения к серверу
            Server.loginUser(LoginActivity.this, email, password, (ProgressBar) findViewById(R.id.progressBar),
                    signUp, loginEmail, loginPassword, findViewById(R.id.loginButton), findViewById(R.id.textView));
        }
        // отслеживаем нажатие на TextView для перехода к регистрации
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // переходим к activity регистрации
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    // метод отслеживающий нажатие кнопки входа
    public void toMain(View view) throws IOException {

        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

        // проверяем корректность электронной почты и пароля
        if (Helper.isLoginDataCorrect(email, password, LoginActivity.this)) {
            // отправляем запрос входа на сервер
            // loginUser принимает контекст, для отправки Toast и создания Intent, ProgressBar, который нужно показывать при загрузке и окна, которые нужно сделать невидимыми во время загрузки
            Server.loginUser(LoginActivity.this, email, password, (ProgressBar) findViewById(R.id.progressBar),
                    signUp, loginEmail, loginPassword, findViewById(R.id.loginButton), findViewById(R.id.textView));
        }

    }


}
