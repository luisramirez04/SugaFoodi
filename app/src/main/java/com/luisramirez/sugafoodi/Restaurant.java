package com.luisramirez.sugafoodi;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    public static List<Restaurant> restaurants = new ArrayList<Restaurant>();

    private String title;
    private List<String> items;

    public Restaurant(String title, String item) {
        this.title = title;
        this.items = new ArrayList<String>();

    }

    public String getTitle() { return title; }

    public List<String> getItems() { return items; }

    public void setTitle(String title) { this.title = title; }

    public void addItem(String item) { this.items.add(item); }

    @Override
    public String toString() {
        return "Restaurant{" +
                "title='" + title + '\'' +
                ", items=" + items +
                '}';
    }
}
