package com.luisramirez.sugafoodi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RestaurantItemsDetailActivity extends AppCompatActivity implements RestaurantItemsListAdapter.Listener {
    RestaurantItemsFragment restaurantsItemsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_items);

        //add fragments dynamically
        //create a fragment object
        restaurantsItemsFragment = new RestaurantItemsFragment();
        restaurantsItemsFragment.setArguments(getIntent().getExtras());
        // get the reference to the FragmentManger object
        FragmentManager fragManager = getSupportFragmentManager();
        // get the reference to the FragmentTransaction object
        FragmentTransaction transaction = fragManager.beginTransaction();
        // add the fragment into the transaction
        transaction.add(R.id.restitemfragframe, restaurantsItemsFragment);
        // commit the transaction.
        transaction.commit();
    }

    @Override
    public void onClick(int position) {
        ItemDetailFragment itemDetailFragment = (ItemDetailFragment) getSupportFragmentManager().findFragmentById(R.id.itemDetailfragContainer);

        if (itemDetailFragment != null) {
            itemDetailFragment.setItem(position);
        } else {
            Intent intent = new Intent(this, ItemDetailActivity.class);
            intent.putExtra("itemid", position);
            startActivity(intent);

        }
    }
}
