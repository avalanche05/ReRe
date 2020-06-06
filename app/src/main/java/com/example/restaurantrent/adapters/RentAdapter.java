package com.example.restaurantrent.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantrent.Helper;
import com.example.restaurantrent.R;
import com.example.restaurantrent.Rent;
import com.example.restaurantrent.Restaurant;
import com.example.restaurantrent.Server;
import com.example.restaurantrent.activities.ChatActivity;
import com.example.restaurantrent.activities.MainActivity;

import java.util.ArrayList;

// адаптер для отображения массива заказов на ListView
public class RentAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<Rent> rents;

    public RentAdapter(Context context, ArrayList<Rent> rents) {
        this.context = context;
        this.rents = rents;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return rents.size();
    }

    @Override
    public Object getItem(int position) {
        return rents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = layoutInflater.inflate(R.layout.rent, parent, false);
        final Rent rent = getRent(position);
        Restaurant restaurant = new Restaurant();
        for (Restaurant temp : MainActivity.restaurants) {
            if (temp.getId() == rent.getIdRestaurant()) {
                restaurant = temp;
                break;
            }
        }
        ((TextView) view.findViewById(R.id.idTextView)).setText("ID: " + rent.getId());
        ((TextView) view.findViewById(R.id.timeTextView)).setText(rent.getTime());
        ((TextView) view.findViewById(R.id.dateTextView)).setText(Helper.convertDateToString(rent.getDate()));
        ((TextView) view.findViewById(R.id.titleTextView)).setText(restaurant.getName());
        ((TextView) view.findViewById(R.id.addressTextView)).setText(restaurant.getAddress());
        // отслеживаем нажатие на кнопку перехода к чату
        ((Button) view.findViewById(R.id.messageButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // отправляем запрос списка всех сообщений по выбранному заказу
                Server.getMessage(rent.getId(), context, new Intent(context, ChatActivity.class), layoutInflater, parent);
            }
        });
        return view;
    }

    public Rent getRent(int position) {
        return rents.get(position);
    }
}
