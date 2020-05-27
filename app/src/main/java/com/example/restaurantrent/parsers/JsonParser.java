package com.example.restaurantrent.parsers;

import com.example.restaurantrent.Rent;
import com.example.restaurantrent.Restaurant;
import com.example.restaurantrent.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonParser {

    public static ArrayList<Table> getTables(String response) throws JSONException {
        JSONArray tablesJson = new JSONArray(response);
        ArrayList<Table> tables = new ArrayList<>();
        for(int i = 0; i < tablesJson.length(); i++){
            JSONObject tableJson = tablesJson.getJSONObject(i);

            tables.add(new Table(tableJson.getLong("id"),tableJson.getLong("idRestaurant"),tableJson.getInt("x"),tableJson.getInt("y"))) ;
        }
        return tables;
    }
    public static ArrayList<Restaurant> getRestaurants(String response) throws JSONException {
        JSONArray restaurantsJson = new JSONArray(response);
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for(int i = 0; i < restaurantsJson.length(); i++){
            JSONObject restaurantJson = restaurantsJson.getJSONObject(i);
            restaurants.add(new Restaurant(restaurantJson.getLong("id"),restaurantJson.getLong("idOwner"),restaurantJson.getString("name"),restaurantJson.getString("address"))) ;
        }
        return restaurants;
    }
    public static String setTablesId(ArrayList<Integer> tables){
        JSONArray jsonArray = new JSONArray();
        for(Integer temp : tables){
            jsonArray.put(temp);
        }
        System.out.println(jsonArray + "");
        return jsonArray + "";
    }
    public static ArrayList<Integer> getIdTables(String response) throws JSONException {
        JSONArray tablesJson = new JSONArray(response);
        ArrayList<Integer> tables = new ArrayList<>();
        for(int i = 0; i < tablesJson.length(); i++){


            tables.add(tablesJson.getInt(i)) ;
        }
        return tables;
    }
    public static ArrayList<Rent> getRents(String response) throws JSONException {
        JSONArray rentJson = new JSONArray(response);
        ArrayList<Rent> rents = new ArrayList<>();
        for(int i = 0;i < rentJson.length(); i++){
            JSONObject restaurantJson = rentJson.getJSONObject(i);
            rents.add(new Rent(restaurantJson.getLong("id"),restaurantJson.getLong("idUser"),restaurantJson.getString("idTables"),restaurantJson.getString("date"),restaurantJson.getLong("idOwner"),restaurantJson.getString("time"),restaurantJson.getLong("idRestaurant")));
        }
        return rents;
    }

}
