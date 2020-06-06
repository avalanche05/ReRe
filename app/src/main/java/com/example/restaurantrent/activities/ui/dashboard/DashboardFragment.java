package com.example.restaurantrent.activities.ui.dashboard;

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
import com.example.restaurantrent.activities.ViewTablesActivity;
import com.example.restaurantrent.adapters.RestaurantAdapter;

// фрагмент со списком всех ресторанов
public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final ListView restaurantList = root.findViewById(R.id.restaurntList);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // отправляем запрос списка всех ресторанов на сервер
                Server.getAllRestaurants();
                // создаём адаптер и присваеваем его restaurantList
                RestaurantAdapter restaurantAdapter = new RestaurantAdapter(getContext(), MainActivity.restaurants);
                restaurantList.setAdapter(restaurantAdapter);
                // отслеживание нажатия на один из элементов restaurantList
                restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        long restaurantId = MainActivity.restaurants.get(position).getId();
                        long ownerId = MainActivity.restaurants.get(position).getIdOwner();
                        // отправляем запрос списка столов для выбранного ресторана на сервер
                        Server.tableGet(restaurantId, ownerId, getActivity(), new Intent(getActivity(), ViewTablesActivity.class));
                    }
                });
            }
        });
        return root;
    }
}
