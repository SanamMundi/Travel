package com.example.travelwithme;

import java.io.Serializable;

public class LocationData implements Serializable {
    private String loc;
    private String title;
    private String category;
    private String address;
    private String icon;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public LocationData(String loc, String title, String category, String address, String icon) {
        this.loc = loc;
        this.title = title;
        this.category = category;
        this.address = address;
        this.icon = icon;
    }
}
