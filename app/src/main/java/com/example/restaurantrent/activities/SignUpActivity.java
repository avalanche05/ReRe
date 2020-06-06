package com.example.restaurantrent.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.restaurantrent.Helper;
import com.example.restaurantrent.R;
import com.example.restaurantrent.Server;

// activity для регистрации
public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registrationButton;

    // поле для закрытия этого activity из другого класса
    public static Activity signUpActivityThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpActivityThis = this;

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        registrationButton = findViewById(R.id.registrationButton);
    }

    // метод отслеживающий нажатие кнопки зарегистрироваться
    public void signUpButton(View view){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // проверяем корректность электронной почты и пароля
        if(Helper.isSignUpDataCorrect(email,password,SignUpActivity.this)){
            // отправляем запрос регистрации на сервер
            // метод signUpUser принимает контекст, для отправки Toast и создания Intent, ProgressBar для отображения загрузки и окна, которые нужно сделать невидимыми во время обращения к серверу
            Server.signUpUser(SignUpActivity.this,email,password,(ProgressBar) findViewById(R.id.progressBar2),emailEditText,passwordEditText,registrationButton);
        }
    }
}
