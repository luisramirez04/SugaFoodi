package com.luisramirez.sugafoodi;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private String id;
    private String name;
    private String restaurant;
    private String type;
    private List<String> reviews;

    public Item() {}

    public Item(String name, String restaurant, String type) {
        this.name = name;
        this.restaurant = restaurant;
        this.type = type;
        reviews = new ArrayList<String>();
    }

    public Item(String id, String name, String restaurant, String type, List<String> reviews) {
        this.id = id;
        this.name = name;
        this.restaurant = restaurant;
        this.type = type;
        this.reviews = reviews;
    }

    public Item(String id, String name, String restaurant, String type) {
        this.id = id;
        this.name = name;
        this.restaurant = restaurant;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", restaurant='" + restaurant + '\'' +
                ", type='" + type + '\'' +
                ", reviews=" + reviews +
                '}';
    }
}
