package com.luisramirez.sugafoodi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class RestaurantItemsFragment extends Fragment {

    int restaurantId;
    TextView titleTextView;

    public RestaurantItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_restaurant_items_list, container, false);

        titleTextView = view.findViewById(R.id.restaurantTitleTextViewId);

        if (getArguments() != null) {
            restaurantId = (int)getArguments().getInt("restaurantid");

        }

        Log.d("restaurantid", " " + restaurantId);
        setRestaurant(restaurantId);

        RecyclerView restaurantsItemsListRecyclerView = (RecyclerView)
                (view.findViewById(R.id.restaurantsitemslist_recyclerview));

        RestaurantItemsListAdapter restaurantItemsListAdapter = new RestaurantItemsListAdapter(Item.items);
        restaurantsItemsListRecyclerView.setAdapter(restaurantItemsListAdapter);
        restaurantItemsListAdapter.setListener((RestaurantItemsListAdapter.Listener)getActivity());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        restaurantsItemsListRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    public void setRestaurant(int restId) {
        restaurantId = restId;
        titleTextView.setText(Restaurant.restaurants.get(restaurantId).getTitle());
    }

    public int getRestaurantId(){
        return restaurantId;
    }
}
