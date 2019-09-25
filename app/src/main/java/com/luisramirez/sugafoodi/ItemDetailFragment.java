package com.luisramirez.sugafoodi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailFragment extends Fragment {

    String itemId, itemName, restaurantName;
    TextView nameTextView, restaurantTextView, typeTextView;
    ImageView foodDetailImage;

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
        foodDetailImage = view.findViewById(R.id.foodDetailImage);

        if (getArguments() != null) {
            itemId = getArguments().getString("itemid");
        }


        setItem(itemId);

        // Access a Cloud Firestore instance from your Activity
        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        Log.d("id", " " + itemId);

        final List<Review> reviews = new ArrayList<>();

        RecyclerView itemReviewsListRecyclerView = (RecyclerView)
                (view.findViewById(R.id.itemreviewlist_recyclerview));

        final ItemReviewsAdapter itemReviewsAdapter = new ItemReviewsAdapter(reviews);
        itemReviewsListRecyclerView.setAdapter(itemReviewsAdapter);
        itemReviewsAdapter.setListener((ItemReviewsAdapter.Listener)getActivity());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        itemReviewsListRecyclerView.setLayoutManager(mLayoutManager);

        DocumentReference docRef = db.collection("items").document(itemId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> reviewIds = (List)documentSnapshot.get("reviews");
                if (reviewIds != null) {
                    for (String id : reviewIds)
                        db.collection("reviews").document(id)
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Review review = documentSnapshot.toObject(Review.class);
                                reviews.add(review);
                                itemReviewsAdapter.notifyDataSetChanged();
                            }
                        });
                }
            }
        });

        return view;
    }

    public void setItem(String itId) {
        itemId = itId;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("items").document(itemId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                nameTextView.setText(String.valueOf(documentSnapshot.get("name")));
                itemName = String.valueOf(documentSnapshot.get("name"));
                restaurantTextView.setText("Restaurant: " + String.valueOf(documentSnapshot.get("restaurant")));
                restaurantName = String.valueOf(documentSnapshot.get("restaurant"));
                typeTextView.setText("Type: " + String.valueOf(documentSnapshot.get("type")));
            }
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images/" + itemId);
        GlideApp.with(getContext())
                .load(storageRef)
                .into(foodDetailImage);

    }

    public String getItemId(){
        return itemId;
    }

    public String getItemName() { return itemName; }

    public String getRestaurantName() { return restaurantName; }



}


