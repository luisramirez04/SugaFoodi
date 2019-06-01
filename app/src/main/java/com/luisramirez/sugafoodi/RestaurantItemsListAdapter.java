package com.luisramirez.sugafoodi;

import android.content.Intent;
import android.net.sip.SipSession;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RestaurantItemsListAdapter extends RecyclerView.Adapter<RestaurantItemsListAdapter.RestaurantItemsListViewHolder>{
    private List<Item> items;
    private Listener listener;

    interface Listener {
        abstract void onClick(int position);
    }

    public RestaurantItemsListAdapter(List<Item> items){ this.items = items;}

    @Override
    public RestaurantItemsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurantitem_list_item, parent,
                        false);
        return new RestaurantItemsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantItemsListViewHolder viewHolder, final int position){
        viewHolder.restItemNameView.setText(items.get(position).getName());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener = (Listener)view.getContext();
                if (listener != null)
                    listener.onClick(position);
            }
        });
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //ViewHolder inner class
    public static class RestaurantItemsListViewHolder extends RecyclerView.ViewHolder {
        private TextView restItemNameView;
        private CardView cardView;
        public RestaurantItemsListViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView;
            restItemNameView = (TextView) itemView.findViewById(R.id.restItemListTitleTextViewId);
        }
    }
}
