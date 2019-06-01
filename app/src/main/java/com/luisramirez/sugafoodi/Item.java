package com.luisramirez.sugafoodi;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Item {

    public static List<Item> items = new ArrayList<Item>();

    private String name;
    private String restaurant;
    private String type;

    public Item(String name, String restaurant, String type) {
        this.name = name;
        this.restaurant = restaurant;
        this.type = type;
        this.items.add(this);

        int i = -1;

        for (int ind = 0; ind < Restaurant.restaurants.size(); ind++) {
            if (Restaurant.restaurants.get(ind).getTitle().equals(restaurant)) {
                i = ind;
            }
        }

        if (i >= 0) {
            Restaurant.restaurants.get(i).addItem(this.name);
        } else {
            Restaurant.restaurants.add(new Restaurant(this.restaurant, this.name));
        }

    }

    public String getName() { return name; }

    public String getRestaurant() { return restaurant; }

    public void setName(String name) { this.name = name; }

    public void addRestaurant(String restaurant) { this.restaurant = restaurant; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", restaurant=" + restaurant +
                ", type=" + type +
                '}';
    }
}
