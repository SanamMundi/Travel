package com.example.travelwithme;

import java.util.ArrayList;

public class Destination {

   // private String name;
    private Restaurants restaurants;
    //private String data;

    public Destination()
    {}

    public Restaurants getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurants restaurants) {
        this.restaurants = restaurants;
    }

    public Destination(Restaurants rest)
    {
      //  this.name = name;
       // this.data = data;
        this.restaurants =rest;

    }
/*
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


*/


}
