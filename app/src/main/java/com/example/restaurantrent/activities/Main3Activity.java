package com.example.restaurantrent.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.restaurantrent.R;
import com.example.restaurantrent.Table;
import com.example.restaurantrent.constant.ActConst;
import com.example.restaurantrent.parsers.JsonParser;
import com.example.restaurantrent.services.HttpService;
;

import org.json.JSONException;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener{

    private Button deleteButton;

    public static int index = 0;

    private boolean flag = true;

    private ArrayList<Integer> selectedTablesId = new ArrayList<>();

    public static ArrayList<Table> tables = new ArrayList<>();

    public static Activity main3ActivityThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        main3ActivityThis = this;

        deleteButton = new Button(this);

        for(int i = 0; i < tables.size(); i++){
            float x = tables.get(i).getX();
            float y = tables.get(i).getY();
            int id = (int)tables.get(i).getId();
            int width = 250;
            int height = 250;
            Button b = new Button(this);
            b.setY(y);
            b.setX(x);
            b.setId(id);
            b.setId((int)tables.get(i).getId());
            b.setOnClickListener(this);
            System.out.println(tables.get(i).getId());

            deleteButton = new Button(this);

            deleteButton.setText(" Удалить бронь ");
            deleteButton.setTextColor(Color.WHITE);
            deleteButton.setBackground(getDrawable(R.drawable.button));
            deleteButton.setX(2);
            deleteButton.setY(60);
            deleteButton.setVisibility(Button.INVISIBLE);
            deleteButton.setOnClickListener(this);
            addContentView(deleteButton,new ViewGroup.LayoutParams(300,140));
            try {
                if(Boolean.parseBoolean(getIntent().getStringExtra("isRent"))&& isIdTablesContains(JsonParser.getIdTables(MainActivity.rents.get(index).getIdTables()),b.getId())){
                    ((Button)findViewById(R.id.rentButton)).setText(" Выйти ");
                    b.setBackground(getDrawable(R.drawable.table_pressed));
                    deleteButton.setVisibility(Button.VISIBLE);
                }
                else {
                    b.setBackground(getDrawable(R.drawable.table));
                }
            } catch (JSONException e) {
                b.setBackground(getDrawable(R.drawable.table));
                e.printStackTrace();
            }

            addContentView(b,new ViewGroup.LayoutParams(250,250));
        }


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == deleteButton.getId()){
            deleteRentButton();
        }
        System.out.println("КНОПКА НАЖАТА С ID: "+ v.getId());
        if(Boolean.parseBoolean(getIntent().getStringExtra("isRent"))){

        }
        else{
            if(selectedTablesId.contains(v.getId())){
                selectedTablesId.remove(new Integer(v.getId()));
                v.setBackground(getDrawable(R.drawable.table));
            }
            else {
                selectedTablesId.add(v.getId());
                v.setBackground(getDrawable(R.drawable.table_pressed));
            }
        }


    }
    public void rentButton(View view){
        if(Boolean.parseBoolean(getIntent().getStringExtra("isRent"))){
            finish();
        }
        else {
            Intent i = new Intent(Main3Activity.this, SelectDateActivity.class);
            i.putExtra("idTables", JsonParser.setTablesId(selectedTablesId));
            System.out.println("ID OWNER IN MAIN 3 ACTIVITY" + getIntent().getLongExtra("idOwner", -1));
            i.putExtra("idOwner", getIntent().getStringExtra("idOwner"));
            startActivity(i);
            selectedTablesId.clear();
        }
    }
    public void deleteRentButton(){
        Intent i = new Intent(Main3Activity.this,HttpService.class);
        i.putExtra("act",ActConst.DELETE_RENT_ACT);
        i.putExtra("id",MainActivity.rents.get(Main3Activity.index).getId());
        startService(i);
        i = new Intent(Main3Activity.this, HttpService.class);
        i.putExtra("act", ActConst.GET_RENTS_ACT);
        i.putExtra("idUser", MainActivity.idUser);
        startService(i);
    }
    public boolean isIdTablesContains(ArrayList<Integer> idTables,int idTable){
        for (int temp : idTables) {
            if (temp == idTable) {
                return true;
            }
        }
        return false;
    }
}

