package com.example.restaurantrent.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.restaurantrent.R;
import com.example.restaurantrent.constant.ActConst;
import com.example.restaurantrent.services.HttpService;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registrationButton;
    public static Activity signUpActivityThis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpActivityThis = this;
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        registrationButton = findViewById(R.id.registrationButton);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Intent i = new Intent(SignUpActivity.this, HttpService.class);
                i.putExtra("act", ActConst.SIGNUP_ACT);
                i.putExtra("email" , email);
                i.putExtra("password", password) ;
                if(!email.isEmpty()&&!password.isEmpty()) {
                    if (LoginActivity.isEmailCorrect(email)){
                        if (password.length()>=8){
                            emailEditText.setVisibility(EditText.INVISIBLE);
                            passwordEditText.setVisibility(EditText.INVISIBLE);
                            registrationButton.setVisibility(Button.INVISIBLE);
                            ((ProgressBar)findViewById(R.id.progressBar2)).setVisibility(ProgressBar.VISIBLE);
                            startService(i);
                        }
                        else {
                            Toast.makeText(SignUpActivity.this,"Пароль должен содержать 8 или более символов",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(SignUpActivity.this,"Формат электронной почты не корректен",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), "Ни одно поле не должно быть пустым", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
