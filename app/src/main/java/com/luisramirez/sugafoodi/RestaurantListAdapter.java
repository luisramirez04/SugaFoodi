package com.luisramirez.sugafoodi;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantListViewHolder>{
    private List<Restaurant> restaurants;
    private Listener listener;

    interface Listener {
        abstract void onClick(String id, int position);
    }

    public RestaurantListAdapter(List<Restaurant> restaurants){this.restaurants = restaurants;}

    @Override
    public RestaurantListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent,
                        false);
        return new RestaurantListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantListViewHolder viewHolder, final int position){
        Log.d("Restaurants", restaurants.get(position).toString());
        viewHolder.restTitleView.setText(restaurants.get(position).getTitle());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener = (Listener)view.getContext();
                if (listener != null)
                    listener.onClick(restaurants.get(position).getId(), position);
            }
        });
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    //ViewHolder inner class
    public static class RestaurantListViewHolder extends RecyclerView.ViewHolder {
        private TextView restTitleView;
        private CardView cardView;
        public RestaurantListViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView;
            restTitleView = (TextView) itemView.findViewById(R.id.restListTitleTextViewId);
        }
    }
}
