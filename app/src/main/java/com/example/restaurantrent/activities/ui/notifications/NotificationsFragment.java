package com.example.restaurantrent.activities.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.restaurantrent.R;
import com.example.restaurantrent.Server;
import com.example.restaurantrent.activities.MainActivity;
import com.example.restaurantrent.activities.ViewRentTablesActivity;
import com.example.restaurantrent.adapters.RentAdapter;

// фрагмент со списком заказов, сделанных пользователем
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
                // отправляем запрос списка заказов, сделанных пользователем, на сервер
                Server.getUserRent(MainActivity.user.getId());

                // создаём адаптер и присваеваем его rentsList
                RentAdapter rentAdapter = new RentAdapter(getContext(), MainActivity.rents);
                rentsList.setAdapter(rentAdapter);
                // отслеживание нажатия на один из элементов restaurantList
                rentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        long restaurantId = MainActivity.rents.get(position).getIdRestaurant();
                        // заполняем поле index порядковым номером выбранного элемента rentsList
                        ViewRentTablesActivity.index = position;
                        // отправляем запрос списка столов для выбранного ресторана на сервер
                        Server.tableGet(restaurantId, MainActivity.rents.get(position).getIdOwner(), getActivity(), new Intent(getActivity(), ViewRentTablesActivity.class));
                    }
                });
            }
        });
        return root;
    }
}