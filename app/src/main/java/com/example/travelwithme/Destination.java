package com.example.travelwithme;

import java.util.ArrayList;

public class Destination {

    private String name;
    private ArrayList<Restaurants> restaurants;
    private String data;

    public Destination()
    {}

    public ArrayList<Restaurants> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<Restaurants> restaurants) {
        this.restaurants = restaurants;
    }

    public Destination(String name, String data)
    {
        this.name = name;
        this.data = data;
        this.restaurants =getRestaurants();

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }





}
