package com.example.travelwithme;

public class LocationData {
    private String loc;
    private String title;
    private String category;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocationData(String loc, String title, String category) {
        this.loc = loc;
        this.title = title;
        this.category = category;
    }
}
