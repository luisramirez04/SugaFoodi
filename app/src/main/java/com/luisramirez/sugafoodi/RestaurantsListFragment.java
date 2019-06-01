package com.luisramirez.sugafoodi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsListFragment extends Fragment {

    public RestaurantsListFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_restaurants_list, container, false);

        RecyclerView restaurantsListRecyclerView = (RecyclerView)
                (v.findViewById(R.id.restaurantslist_recyclerview));

        RestaurantListAdapter restaurantListAdapter = new RestaurantListAdapter(Restaurant.restaurants);
        restaurantsListRecyclerView.setAdapter(restaurantListAdapter);
        restaurantListAdapter.setListener((RestaurantListAdapter.Listener)getActivity());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        restaurantsListRecyclerView.setLayoutManager(mLayoutManager);

        return v;
    }

}
