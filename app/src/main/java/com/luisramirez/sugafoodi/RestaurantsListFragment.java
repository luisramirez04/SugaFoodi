package com.luisramirez.sugafoodi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsListFragment extends Fragment {

    private final String TAG = "LOG: ";
    final List<Restaurant> restaurants = new ArrayList<Restaurant>();
    final RestaurantListAdapter restaurantListAdapter = new RestaurantListAdapter(restaurants);

    public RestaurantsListFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_restaurants_list, container, false);

        RecyclerView restaurantsListRecyclerView = (RecyclerView)
                (v.findViewById(R.id.restaurantslist_recyclerview));


        restaurantsListRecyclerView.setAdapter(restaurantListAdapter);
        restaurantListAdapter.setListener((RestaurantListAdapter.Listener)getActivity());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        restaurantsListRecyclerView.setLayoutManager(mLayoutManager);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("restaurants")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Restaurant restaurant = document.toObject(Restaurant.class);
                                restaurants.add(restaurant);
                                restaurantListAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        restaurantListAdapter.notifyDataSetChanged();
    }
}
