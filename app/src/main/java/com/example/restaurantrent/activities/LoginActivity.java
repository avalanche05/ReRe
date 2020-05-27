package com.example.restaurantrent.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantrent.R;
import com.example.restaurantrent.constant.ActConst;
import com.example.restaurantrent.services.HttpService;

public class LoginActivity extends AppCompatActivity {
    private TextView signUp;
    private EditText loginEmail;
    private EditText loginPassword;

    public static Activity loginActivityThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivityThis = this;
        loginEmail = findViewById(R.id.emailEditText);
        loginPassword = findViewById(R.id.passwordEditText);
        signUp = findViewById(R.id.registrationTextView);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }
    public void toMain(View view){
        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();
        if(email.isEmpty()||password.isEmpty()){
            Toast.makeText(LoginActivity.this,"Ни одно поле не должно быть пустым",Toast.LENGTH_SHORT);
        }
        else if(isEmailCorrect(email)) {
            if (password.length() >= 8){
                Intent i = new Intent(LoginActivity.this, HttpService.class);
                i.putExtra("act", ActConst.LOGIN_ACT);
                i.putExtra("email",email);
                i.putExtra("password",password);

                signUp.setVisibility(TextView.INVISIBLE);
                loginEmail.setVisibility(EditText.INVISIBLE);
                loginPassword.setVisibility(EditText.INVISIBLE);
                ((Button)findViewById(R.id.button)).setVisibility(Button.INVISIBLE);
                ((TextView)findViewById(R.id.textView)).setVisibility(Button.INVISIBLE);
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(ProgressBar.VISIBLE);

                startService(i);
            }
            else {
                Toast.makeText(LoginActivity.this,"Пароль должен содержать 8 или более символов",Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(LoginActivity.this,"Формат электронной почты не корректен",Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isEmailCorrect(String email){
        if(email.contains("@")){
            email = email.split("@")[1];
            if(email.contains(".")){
                return true;
            }
        }
        return false;
    }
}
