package com.luisramirez.sugafoodi;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Review {

    private String id;
    private String comments;
    private float stars;
    private String restaurant;
    private String item;

    public Review() {}

    public Review(String comments, float stars, String restaurant, String item) {
        this.comments = comments;
        this.stars = stars;
        this.restaurant = restaurant;
        this.item = item;
    }

    public Review(String id, String comments, float stars, String restaurant, String item) {
        this.id = id;
        this.comments = comments;
        this.stars = stars;
        this.restaurant = restaurant;
        this.item = item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}

