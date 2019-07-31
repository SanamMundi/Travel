package com.example.travelwithme;

import java.util.ArrayList;

public class SectionalDataModel {

    private String headerTitle;
    private ArrayList<Destination> destinations;

    public SectionalDataModel()
    {}

    public SectionalDataModel(String headerTitle, ArrayList<Destination> destinations)
    {
        this.headerTitle = headerTitle;
        this.destinations = destinations;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(ArrayList<Destination> destinations) {
        this.destinations = destinations;
    }
}
