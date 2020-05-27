package com.example.restaurantrent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.restaurantrent.R;
import com.example.restaurantrent.Rent;
import com.example.restaurantrent.Restaurant;
import com.example.restaurantrent.activities.MainActivity;

import java.util.ArrayList;

public class RentAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private Context context;
        private ArrayList<Rent> rents;

        public RentAdapter(Context context, ArrayList<Rent> rents){
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if(view == null)
                view = layoutInflater.inflate(R.layout.rent,parent,false);
            Rent rent = getRent(position);
            Restaurant restaurant = new Restaurant();
            for (Restaurant temp : MainActivity.restaurants){
                if(temp.getId() == rent.getIdRestaurant()){
                    restaurant = temp;
                    break;
                }
            }
            ((TextView) view.findViewById(R.id.idTextView)).setText("ID: " + rent.getId());
            ((TextView) view.findViewById(R.id.timeTextView)).setText(rent.getTime());
            ((TextView) view.findViewById(R.id.dateTextView)).setText(rent.getDate());
            ((TextView) view.findViewById(R.id.titleTextView)).setText(restaurant.getName());
            ((TextView) view.findViewById(R.id.addressTextView)).setText(restaurant.getAddress());
            return view;
        }
        public Rent getRent(int position) {
            return rents.get(position);
        }
}
