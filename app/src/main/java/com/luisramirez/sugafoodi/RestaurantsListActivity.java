package com.luisramirez.sugafoodi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class RestaurantsListActivity extends AppCompatActivity implements RestaurantListAdapter.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddItemActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(String id, int position) {
        RestaurantItemsFragment detailFragment =
                (RestaurantItemsFragment) getSupportFragmentManager().findFragmentById(R.id.detailfragment);

        if (detailFragment != null) {
            detailFragment.setRestaurant(id);
        } else {
            Intent intent = new Intent(this, RestaurantItemsDetailActivity.class);
            intent.putExtra("RestaurantId", id);
            intent.putExtra("Restaurantposition", position);
            startActivity(intent);

        }
    }
}

