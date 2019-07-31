package com.example.travelwithme;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReceiveData {
    Destination destination = new Destination();
    GetData data = new GetData();
    String myData;

    public ArrayList<Restaurants> receiveData(String lat,String lng,String type)
    {

       myData=data.getData("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +lat+","+lng+"&radius=5000&type="+type+"&key=AIzaSyBYNwaeGibgmiD_43QTVQ4F-YkVkWeM00w");
       return restaurantData(myData);

    }

    public ArrayList<Restaurants> restaurantData(String data)
    {
        try
        {
            ArrayList<Restaurants> restaurants = new ArrayList<>();
            Restaurants rest;

            JSONObject obj = new JSONObject(data);
            JSONArray jsonArray = obj.getJSONArray("results");
            Log.d("len",jsonArray.length()+"");
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject obj1 = jsonArray.getJSONObject(i);
                rest = new Restaurants();
                rest.setName(obj1.getString("name"));
                rest.setIcon(obj1.getString("icon"));
                rest.setId(obj1.getString("place_id"));
                rest.setRatings(obj1.getString("rating"));
                rest.setVicinity(obj1.getString("vicinity"));
                JSONObject geo = obj1.getJSONObject("geometry");
                JSONObject loc = geo.getJSONObject("location");
                rest.setLat(loc.getString("lat"));
                rest.setLng(loc.getString("lng"));
                JSONArray pic = obj1.getJSONArray("photos");
                JSONObject pic1 = pic.getJSONObject(0);
                rest.setPhoto(pic1.getString("photo_reference"));
                restaurants.add(rest);


            }


            return restaurants;
          //destination.setRestaurants(restaurants);

          /* for(int i =1; i<restaurants.size();i++)
          {
                Log.d("name",destination.getRestaurants().get(i).getName()+"");

          }*/
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }


}
