package com.example.restaurantrent.activities.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.restaurantrent.constant.ActConst;
import com.example.restaurantrent.activities.MainActivity;
import com.example.restaurantrent.R;
import com.example.restaurantrent.adapters.RestaurantAdapter;
import com.example.restaurantrent.services.HttpService;

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

                Intent intent = new Intent(getActivity(), HttpService.class);
                intent.putExtra("act", ActConst.GET_ALL_RESTAURANTS_ACT);
                getActivity().startService(intent);

                RestaurantAdapter restaurantAdapter = new RestaurantAdapter(getContext(), MainActivity.restaurants);
                restaurantList.setAdapter(restaurantAdapter);
                restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        long restaurantId = MainActivity.restaurants.get(position).getId();
                        long ownerId = MainActivity.restaurants.get(position).getIdOwners();
                        System.out.println("OWNER ID IN DASHBOARD FRAGMENT"+ownerId);
                        Intent i = new Intent(getActivity(), HttpService.class);
                        i.putExtra("act", ActConst.GET_TABLES_ACT);
                        i.putExtra("idRestaurant",restaurantId);
                        i.putExtra("idOwner",ownerId);
                        getActivity().startService(i);
                    }
                });
            }
        });
        return root;
    }
}
