package com.example.restaurantrent.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.restaurantrent.R;
import com.example.restaurantrent.constant.ActConst;
import com.example.restaurantrent.services.HttpService;

import java.util.Calendar;
import java.util.Date;

public class SelectDateActivity extends AppCompatActivity {
    EditText dateEditText,timeEditText;

    private int mYear, mMonth, mDay, mHour, mMinute;

    public static Activity selectDateActivityThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        selectDateActivityThis = this;
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);


    }
    public void rent(View view){
        Intent i = new Intent(SelectDateActivity.this, HttpService.class);
        i.putExtra("act", ActConst.ADD_RENT_ACT);
        i.putExtra("idOwner",getIntent().getStringExtra("idOwner"));
        i.putExtra("idUser",MainActivity.idUser);
        i.putExtra("idTables",getIntent().getStringExtra("idTables"));
        i.putExtra("date",dateEditText.getText().toString());
        i.putExtra("time",timeEditText.getText().toString());
        startService(i);
    }

    public void callTimePicker(View view) {
        // получаем текущее время
        final Calendar cal = Calendar.getInstance();
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        // инициализируем диалог выбора времени текущими значениями
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String editTextTimeParam = zero(hourOfDay) + ":" + zero(minute);
                        timeEditText.setText(editTextTimeParam);
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    public void callDatePicker(View view) {
        // получаем текущую дату
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        // инициализируем диалог выбора даты текущими значениями
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String editTextDateParam = zero(dayOfMonth) + "." + zero(monthOfYear + 1) + "." + zero(year);
                        dateEditText.setText(editTextDateParam);
                    }
                }, mYear, mMonth, mDay);
        long currentTime = new Date().getTime();
        datePickerDialog.getDatePicker().setMinDate(currentTime);
        datePickerDialog.show();
    }
    public String zero(int i){
        if(i < 10) {
            return "0" + i;
        }
        return i+"";

    }
}
