package com.luisramirez.sugafoodi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;


public class AddReviewActivity extends AppCompatActivity {

    EditText commentsEditText;
    RatingBar starsRatingBar;
    float stars = 5;
    String itemId, itemName, restaurantName;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review);

        if (getIntent().getExtras() != null) {
            itemId = getIntent().getExtras().getString("itemid");
            itemName = getIntent().getExtras().getString("itemname");
            restaurantName = getIntent().getExtras().getString("itemrestaurant");

        }

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();


        Button submitButton = (Button) findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubmit(view);
            }
        });
        starsRatingBar = findViewById(R.id.ratingBarReview);
        starsRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                stars = rating;
            }
        });
    }

    public void onClickSubmit(View v) {
        commentsEditText = findViewById(R.id.editTextReviewComments);
        String ratingComments = commentsEditText.getText().toString();

        DocumentReference reviewRef = db.collection("reviews").document();

        Review review = new Review(reviewRef.getId(), ratingComments, stars, restaurantName, itemName);
        reviewRef.set(review);

        db.collection("items").document(itemId)
                .update("reviews", FieldValue.arrayUnion(reviewRef.getId()));

        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra("itemid", itemId);
        startActivity(intent);
        finish();
    }



}
