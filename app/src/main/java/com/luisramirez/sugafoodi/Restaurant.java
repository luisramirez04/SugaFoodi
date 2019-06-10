package com.luisramirez.sugafoodi;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private String id;
    private String title;
    private List<String> items;

    public Restaurant() {}

    public Restaurant(String id, String title, String item) {
        this.id = id;
        this.title = title;
        this.items = new ArrayList<String>();
        this.items.add(item);
    }

    public Restaurant(String id, String title, List<String> items) {
        this.id = id;
        this.title = title;
        this.items = new ArrayList<String>(items);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() { return title; }

    public List<String> getItems() { return items; }

    public void setTitle(String title) { this.title = title; }

    public void addItem(String item) { this.items.add(item); }

    @Override
    public boolean equals(Object obj) {
        Restaurant rest = (Restaurant) obj;
        return rest.title == this.title;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "title='" + title + '\'' +
                ", items=" + items +
                '}';
    }
}
