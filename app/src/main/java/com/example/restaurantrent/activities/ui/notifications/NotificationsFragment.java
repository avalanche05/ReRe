package com.example.restaurantrent.activities.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.restaurantrent.R;
import com.example.restaurantrent.activities.Main3Activity;
import com.example.restaurantrent.activities.MainActivity;
import com.example.restaurantrent.adapters.RentAdapter;
import com.example.restaurantrent.adapters.RestaurantAdapter;
import com.example.restaurantrent.constant.ActConst;
import com.example.restaurantrent.services.HttpService;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final ListView rentsList = root.findViewById(R.id.rentsListView);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Intent i = new Intent(getActivity(), HttpService.class);
                i.putExtra("act", ActConst.GET_RENTS_ACT);
                i.putExtra("idUser", MainActivity.idUser);
                getActivity().startService(i);
                RentAdapter rentAdapter = new RentAdapter(getContext(),MainActivity.rents);
                rentsList.setAdapter(rentAdapter);
                rentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        long restaurantId = MainActivity.rents.get(position).getIdRestaurant();
                        Main3Activity.index = position;
                        Intent i = new Intent(getActivity(), HttpService.class);
                        i.putExtra("act", ActConst.GET_TABLES_ACT);
                        i.putExtra("isRent",true);
                        i.putExtra("idRestaurant",restaurantId);
                        getActivity().startService(i);
                    }
                });
            }
        });
        return root;
    }
}