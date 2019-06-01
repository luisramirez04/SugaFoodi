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

public class ItemDetailFragment extends Fragment {

    int itemId;
    TextView nameTextView, restaurantTextView, typeTextView;

    public ItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);

        nameTextView = view.findViewById(R.id.itemNameTextViewId);
        restaurantTextView = view.findViewById(R.id.itemRestaurantTextViewId);
        typeTextView = view.findViewById(R.id.itemTypeTextViewId);

        if (getArguments() != null) {
            int itemId = (int)getArguments().getInt("itemid");
        }

        Log.d("itemid", " " + itemId);
        setItem(itemId);

        RecyclerView itemReviewsListRecyclerView = (RecyclerView)
                (view.findViewById(R.id.itemreviewlist_recyclerview));

        ItemReviewsAdapter itemReviewsAdapter = new ItemReviewsAdapter(Review.reviews);
        itemReviewsListRecyclerView.setAdapter(itemReviewsAdapter);
        itemReviewsAdapter.setListener((ItemReviewsAdapter.Listener)getActivity());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        itemReviewsListRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    public void setItem(int itId) {
        itemId = itId;
        nameTextView.setText(Item.items.get(itemId).getName());
        restaurantTextView.setText("Restaurant: " + Item.items.get(itemId).getRestaurant());
        typeTextView.setText("Type: " + Item.items.get(itemId).getType());
    }

    public int getItemId(){
        return itemId;
    }
}
