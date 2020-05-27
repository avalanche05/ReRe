package com.example.restaurantrent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restaurantrent.R;
import com.example.restaurantrent.Restaurant;

import java.util.ArrayList;

public class RestaurantAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<Restaurant> restaurants;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurants){
        this.context = context;
        this.restaurants = restaurants;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null)
            view = layoutInflater.inflate(R.layout.restaurant,parent,false);
        Restaurant restaurant = getRestaurant(position);
        ((ImageView) view.findViewById(R.id.restaurantPicture)).setImageResource(R.drawable.logoicon);
        ((TextView) view.findViewById(R.id.name)).setText(restaurant.getName());
        ((TextView) view.findViewById(R.id.address)).setText(restaurant.getAddress());
        return view;
    }
    public Restaurant getRestaurant(int position) {
        return restaurants.get(position);
    }
}