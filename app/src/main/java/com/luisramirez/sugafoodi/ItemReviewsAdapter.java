package com.luisramirez.sugafoodi;

import android.content.Intent;
import android.media.Rating;
import android.net.sip.SipSession;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class ItemReviewsAdapter extends RecyclerView.Adapter<ItemReviewsAdapter.ItemReviewsListViewHolder>{
    private List<Review> reviews;
    private Listener listener;

    interface Listener {
        abstract void onClick(String id, int position);
    }

    public ItemReviewsAdapter(List<Review> reviews){ this.reviews = reviews;}

    @Override
    public ItemReviewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_list_item, parent,
                        false);
        return new ItemReviewsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemReviewsListViewHolder viewHolder, final int position){
        viewHolder.itemReviewCommentTextView.setText(reviews.get(position).getComments());
        viewHolder.itemReviewStarsRatingBar.setRating(reviews.get(position).getStars());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener = (Listener)view.getContext();
                if (listener != null)
                    listener.onClick(reviews.get(position).getId(),
                            position);
            }
        });
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    //ViewHolder inner class
    public static class ItemReviewsListViewHolder extends RecyclerView.ViewHolder {
        private TextView itemReviewCommentTextView;
        private RatingBar itemReviewStarsRatingBar;
        private CardView cardView;
        public ItemReviewsListViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView;
            itemReviewCommentTextView = (TextView) itemView.findViewById(R.id.itemReviewsListCommentsTextViewId);
            itemReviewStarsRatingBar = (RatingBar) itemView.findViewById(R.id.itemReviewsListStarsRatingBarId);
        }
    }
}
