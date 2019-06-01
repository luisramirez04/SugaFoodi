package com.luisramirez.sugafoodi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ItemDetailActivity extends AppCompatActivity {
    ItemDetailFragment itemDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        //add fragments dynamically
        //create a fragment object
        itemDetailFragment = new ItemDetailFragment();
        itemDetailFragment.setArguments(getIntent().getExtras());
        // get the reference to the FragmentManger object
        FragmentManager fragManager = getSupportFragmentManager();
        // get the reference to the FragmentTransaction object
        FragmentTransaction transaction = fragManager.beginTransaction();
        // add the fragment into the transaction
        transaction.add(R.id.itemDetailfragContainer, itemDetailFragment);
        // commit the transaction.
        transaction.commit();
    }

//    @Override
//    public void onClick(int position) {
////        RestaurantItemsFragment detailFragment = (RestaurantItemsFragment) getSupportFragmentManager().findFragmentById(R.id.detailfragment);
////
////        if (detailFragment != null) {
////            detailFragment.setRestaurant(position);
////        } else {
////            Intent intent = new Intent(this, RestaurantItemsDetailActivity.class);
////            intent.putExtra("restaurantid", position);
////            startActivity(intent);
////
////        }
//    }
}
