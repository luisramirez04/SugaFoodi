package com.luisramirez.sugafoodi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RestaurantItemsFragment extends Fragment {

    Restaurant restaurant;
    String restaurantId;
    int restaurantPosition;
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
            restaurantId = getArguments().getString("RestaurantId");
        }

        setRestaurant(restaurantId);

        // Access a Cloud Firestore instance from your Activity
        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        Log.d("id", " " + restaurantId);
        Log.d("position", " " + restaurantPosition);

        final List<Item> itemList = new ArrayList<>();

        RecyclerView restaurantsItemsListRecyclerView = (RecyclerView)
                (view.findViewById(R.id.restaurantsitemslist_recyclerview));

        final RestaurantItemsListAdapter restaurantItemsListAdapter = new RestaurantItemsListAdapter(itemList);
        restaurantsItemsListRecyclerView.setAdapter(restaurantItemsListAdapter);
        restaurantItemsListAdapter.setListener((RestaurantItemsListAdapter.Listener)getActivity());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        restaurantsItemsListRecyclerView.setLayoutManager(mLayoutManager);

        DocumentReference docRef = db.collection("restaurants").document(restaurantId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> itemids = (List)documentSnapshot.get("items");
                for(String id: itemids)
                    db.collection("items").document(id)
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Item item = documentSnapshot.toObject(Item.class);
                                itemList.add(item);
                                restaurantItemsListAdapter.notifyDataSetChanged();
                            }
                        });

            }
        });




        return view;
    }

    public void setRestaurant(String restId) {
        restaurantId = restId;


        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("restaurants").document(restaurantId)
            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                titleTextView.setText(String.valueOf(documentSnapshot.get("title")));
            }
        });


    }
//
    public String getRestaurantId(){
        return restaurantId;
    }
}
