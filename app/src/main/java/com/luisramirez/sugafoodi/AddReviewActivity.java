package com.luisramirez.sugafoodi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class AddReviewActivity extends AppCompatActivity {

    EditText commentsEditText;
    RatingBar starsRatingBar;
    float stars = 5;
    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review);

        if (getIntent().getExtras() != null) {
            int itemId = getIntent().getExtras().getInt("itemid");
        }


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
        String restaurant = Item.items.get(itemId).getRestaurant();
        String item = Item.items.get(itemId).getName();

        Review newReview = new Review(ratingComments, stars, restaurant, item);

        Intent intent = new Intent(this, ItemDetailActivity.class);
        startActivity(intent);
    }



}
