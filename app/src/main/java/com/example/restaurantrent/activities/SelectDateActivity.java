package com.example.restaurantrent.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantrent.R;
import com.example.restaurantrent.Rent;
import com.example.restaurantrent.Server;

import java.util.Calendar;
import java.util.Date;

// activity для выбора даты
public class SelectDateActivity extends AppCompatActivity {
    EditText dateEditText, timeEditText;

    // поля для закрытия этого activity из другого класса
    public static Activity selectDateActivityThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

        selectDateActivityThis = this;
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);


    }

    // метод отслеживающий нажатие кнопки отправки брони
    public void rent(View view) {
        // закрываем ViewTablesActivity, чтобы вернуться к MainActivity
        ViewTablesActivity.viewTablesActivityThis.finish();
        Server.rentAdd(new Rent(getIntent().getLongExtra("idOwner", -1), MainActivity.user.getId(), getIntent().getStringExtra("idSelectedTables"), dateEditText.getText().toString(), timeEditText.getText().toString()), SelectDateActivity.this);

    }

    // метод отслеживающий нажатие на кнопку выбора времени
    public void callTimePicker(View view) {
        // получаем текущее время
        final Calendar cal = Calendar.getInstance();
        int mHour = cal.get(Calendar.HOUR_OF_DAY);
        int mMinute = cal.get(Calendar.MINUTE);

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

    // метод отслеживающий нажатие на кнопку выбора даты
    public void callDatePicker(View view) {
        // получаем текущую дату
        final Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);

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
        // запрещаем выбирать дату, которая ранее, чем текущая
        datePickerDialog.getDatePicker().setMinDate(currentTime);
        datePickerDialog.show();
    }

    // метод, который приписывает ноль слева, если цифра меньше 10
    public String zero(int i) {
        if (i < 10) {
            return "0" + i;
        }
        return i + "";

    }
}
