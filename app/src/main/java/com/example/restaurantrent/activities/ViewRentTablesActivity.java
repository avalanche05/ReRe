package com.example.restaurantrent.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantrent.Board;
import com.example.restaurantrent.Helper;
import com.example.restaurantrent.R;
import com.example.restaurantrent.Server;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

// activity для просмотра выбранных столов в конкретном заказе
public class ViewRentTablesActivity extends AppCompatActivity {
    // поле текущего индекса заказа(присваевается в NotificationFragment)
    public static int index = 0;

    private TextView rentInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rent_tables);

        rentInfoTextView = findViewById(R.id.rentInfoTextView);
        // ставим в TextView информацию о текущем заказе
        rentInfoTextView.setText(Helper.getRentInfo(MainActivity.rents.get(index)));

        // берём массив столов конкретного ресторана из класса Server
        ArrayList<Board> tables = (ArrayList<Board>) getIntent().getSerializableExtra("tables");
        for (Board table : tables) {
            // переводим класс Board в класс Button
            Button button = Helper.convertTableFromButton(table, this);
            // проверяем является ли стол забронированным
            if (parseJsonFromArray(MainActivity.rents.get(index).getIdTables()).contains(table.getId())) {
                // рисуем стол нажатым
                button.setBackground(getDrawable(R.drawable.table_pressed));
            }
            // добавляем кнопку в окно
            addContentView(button, new ViewGroup.LayoutParams(250, 250));
        }
    }

    // метод отслеживающий нажатие кнопки выхода
    public void outButton(View view) {
        // закрываем текущее activity
        finish();
    }

    // метод отслеживающий нажатие кнопки удаления заказа
    public void deleteRentButton(View view) {
        // отправляем запрос удаления заказа на сервер
        Server.rentDelete(MainActivity.rents.get(index).getId(), ViewRentTablesActivity.this);
        // отправляем запрос списка всех заказов конкретного пользователя
        Server.getUserRent(MainActivity.user.getId());
    }

    // метод разбора Json в ArrayList
    private ArrayList<Long> parseJsonFromArray(String idTables) {
        Gson gson = new Gson();
        ArrayList<Long> arrayIdTables = gson.fromJson(idTables, new TypeToken<ArrayList<Long>>() {
        }.getType());
        return arrayIdTables;
    }
}
