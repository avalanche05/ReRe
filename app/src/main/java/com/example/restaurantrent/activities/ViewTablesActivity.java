package com.example.restaurantrent.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantrent.Board;
import com.example.restaurantrent.Helper;
import com.example.restaurantrent.R;
import com.google.gson.Gson;

import java.util.ArrayList;

// activity для просмотра столов конкретного ресторана
public class ViewTablesActivity extends AppCompatActivity implements View.OnClickListener {

    // поле для закрытия этого activity из другого класса
    public static Activity viewTablesActivityThis;

    // массив выбранных столов(стол добавляется, при нажатии на него)
    private ArrayList<Integer> idSelectedTables = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tables);

        viewTablesActivityThis = this;
        // берём массив столов конкретного ресторана из класса Server
        ArrayList<Board> tables = (ArrayList<Board>) getIntent().getSerializableExtra("tables");
        for (Board table : tables) {
            // переводим класс Board в класс Button
            Button button = Helper.convertTableFromButton(table, this,this);
            // добавляем кнопку в окно
            addContentView(button, new ViewGroup.LayoutParams(250, 250));
        }
    }

    @Override
    public void onClick(View v) {

        // проверяем, не была ли кнопка добавлена в массив ранее, если была, кнопка удаляется из массива
        if (idSelectedTables.remove(new Integer(v.getId()))) {
            // рисуем стол не выбранным
            v.setBackground(getDrawable(R.drawable.table));
        } else {
            // кнопка добавляется в массив
            idSelectedTables.add(new Integer(v.getId()));
            // рисуем стол выбранным
            v.setBackground(getDrawable(R.drawable.table_pressed));
        }

    }

    // метод отслеживающий нажатие кнопки продолжения создания заказа
    public void continueRentButton(View view) {
        // проверяем выбран ли хоть один стол
        if (idSelectedTables.size() > 0) {
            // переходим в SelectDateActivity
            Intent i = new Intent(ViewTablesActivity.this, SelectDateActivity.class);
            Gson gson = new Gson();
            // кладём в Intent все выбранные столы и id владельца выбранного ресторана
            i.putExtra("idSelectedTables", gson.toJson(idSelectedTables));
            i.putExtra("idOwner", getIntent().getLongExtra("idOwner", -1));
            startActivity(i);
        } else {
            // уведомляем, что выбрать нужно хотя бы один стол
            Toast.makeText(ViewTablesActivity.this, "Выберите минимум 1 стол", Toast.LENGTH_SHORT).show();
        }

    }

    // метод, отслеживающий кнопку отмены создания заказа
    public void cancelRentButton(View view) {
        // закрываем текущее activity
        finish();
    }
}
