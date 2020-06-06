package com.example.restaurantrent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.restaurantrent.activities.MainActivity;

public class Helper {

    public static final String APP_PREFERENCES = "owner";
    public static final String APP_PREFERENCES_EMAIL = "email";
    public static final String APP_PREFERENCES_PASSWORD = "password";

    public static boolean isLoginDataCorrect(String email, String password, Context context){
        if(email.isEmpty()||password.isEmpty()){
            Toast.makeText(context,"Ни одно поле не должно быть пустым",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isEmailCorrect(email)){
            Toast.makeText(context,"Формат электронной почты не корректен",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH){
            Toast.makeText(context,"Пароль должен содержать "+ MINIMUM_PASSWORD_LENGTH +" или более символов",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean isSignUpDataCorrect(String email, String password, Context context){
        if(email.isEmpty()||password.isEmpty()){
            Toast.makeText(context,"Ни одно поле не должно быть пустым",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isEmailCorrect(email)){
            Toast.makeText(context,"Формат электронной почты не корректен",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH){
            Toast.makeText(context,"Пароль должен содержать "+ MINIMUM_PASSWORD_LENGTH +" или более символов",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static Button convertTableFromButton(Board table, Activity activity, View.OnClickListener onClickListener){
        Button button = new Button(activity);
        button.setId((int)table.getId());
        button.setX(table.getX());
        button.setY(table.getY());
        button.setBackground(activity.getDrawable(R.drawable.table));
        button.setTextColor(Color.WHITE);
        button.setOnClickListener(onClickListener);
        return button;
    }

    public static Button convertTableFromButton(Board table, Activity activity){
        Button button = new Button(activity);
        button.setId((int)table.getId());
        button.setX(table.getX());
        button.setY(table.getY());
        button.setBackground(activity.getDrawable(R.drawable.table));
        button.setTextColor(Color.WHITE);
        return button;
    }

    public static String convertDateToString(String date){
        String[] numberDate = date.split("\\.");
        String[] month = {"Января","Февраля","Марта","Апреля","Мая","Июня","Июля","Августа","Сентября","Ноября","Октября","Декабря"};
        return numberDate[0] + " " + month[Integer.parseInt(numberDate[1])-1];
    }

    public static String getRentInfo(Rent rent){

        return rent.getTime() + "\n" +
                convertDateToString(rent.getDate())+"\n" +
                findRestaurantById(rent.getIdRestaurant()).getName()+"\n" +
                findRestaurantById(rent.getIdRestaurant()).getAddress();
    }

    private static Restaurant findRestaurantById(long id){
        for(Restaurant temp:MainActivity.restaurants){
            if(temp.getId() == id)
                return temp;
        }
        return null;
    }

    final private static int MINIMUM_PASSWORD_LENGTH = 8;

    private static boolean isEmailCorrect(String email){
        if(email.contains("@")){
            email = email.split("@")[1];
            if(email.contains(".")){
                return true;
            }
        }
        return false;
    }

}
