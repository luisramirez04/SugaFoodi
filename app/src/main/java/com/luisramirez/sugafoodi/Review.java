package com.luisramirez.sugafoodi;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Review {

    public static List<Review> reviews = new ArrayList<Review>();

    private String comments;
    private float stars;
    private String restaurant;
    private String item;

    public Review(String comments, float stars, String restaurant, String item) {
        this.comments = comments;
        this.stars = stars;
        this.restaurant = restaurant;
        this.reviews.add(this);

    }

    public String getComments() { return comments; }

    public float getStars() { return stars; }

    public String getRestaurant() { return restaurant; }

    @Override
    public String toString() {
        return "Review{" +
                "comments='" + comments + '\'' +
                ", stars=" + stars +
                ", restaurant='" + restaurant + '\'' +
                ", item='" + item + '\'' +
                '}';
    }

    }

