package com.example.travelwithme;

public class Destination {

    private String name;
    private String data;

    public Destination()
    {}

    public Destination(String name, String data)
    {
        this.name = name;
        this.data = data;

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
