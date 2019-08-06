package com.example.travelwithme;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReceiveData {
    Destination destination = new Destination();
    GetData data = new GetData();
    String myData;

    public ArrayList<Data> receiveData(String lat,String lng,String type)
    {

       myData=data.getData("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +lat+","+lng+"&radius=5000&type="+type+"&key=AIzaSyBYNwaeGibgmiD_43QTVQ4F-YkVkWeM00w");
       return restaurantData(myData);
    }

    public ArrayList<Data> restaurantData(String data)
    {
        try
        {

            ArrayList<Data> myData = new ArrayList<>();
            Data data1;

            JSONObject obj = new JSONObject(data);
            JSONArray jsonArray = obj.getJSONArray("results");

            for(int i=0;i<jsonArray.length();i++) {
                JSONObject obj1 = jsonArray.getJSONObject(i);
                data1 = new Data();
                data1.setName(obj1.getString("name"));
                data1.setIcon(obj1.getString("icon"));
                data1.setId(obj1.getString("place_id"));
                if(obj1.has("rating"))
                    data1.setRatings(obj1.getString("rating"));
                else
                    data1.setRatings("0.0");

                if(obj1.has("vicinity"))
                    data1.setVicinity(obj1.getString("vicinity"));
                else
                    data1.setVicinity("Not available");
                JSONObject geo = obj1.getJSONObject("geometry");
                JSONObject loc = geo.getJSONObject("location");
                data1.setLat(loc.getString("lat"));
                data1.setLng(loc.getString("lng"));
                if(obj1.has("photos")){
                    JSONArray pic = obj1.getJSONArray("photos");
                    JSONObject pic1 = pic.getJSONObject(0);
                    data1.setPhoto(pic1.getString("photo_reference"));}
                else
                    data1.setPhoto("Not available");
                myData.add(data1);



            }


            return myData;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    public City cityData(String data)
    {

        Log.d("1234567890", "hello");
        try {
            City city = new City();

            JSONObject obj = new JSONObject(data);
            JSONArray jsonArray = obj.getJSONArray("results");
            JSONObject obj1 = jsonArray.getJSONObject(0);
            JSONObject geo = obj1.getJSONObject("geometry");
            JSONObject loc = geo.getJSONObject("location");
            city.setLat(loc.getString("lat"));
            city.setLng(loc.getString("lng"));


            Log.d("1234567", loc.getString("lat"));
            return city;





        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String getPhotoReference(String data)
    {
        try
        {
            JSONObject obj = new JSONObject(data);
            JSONArray jsonArray = obj.getJSONArray("results");
            JSONObject obj1 = jsonArray.getJSONObject(0);
            if(obj1.has("photos")) {
                JSONArray pic = obj1.getJSONArray("photos");
                JSONObject pic1 = pic.getJSONObject(0);
                return pic1.getString("photo_reference");

            }
            else
                return "";

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }




    public String getPlaceId(String data)
    {
        try
        {
            JSONObject obj = new JSONObject(data);
            JSONArray jsonArray = obj.getJSONArray("results");


            JSONObject obj1 = jsonArray.getJSONObject(0);
            String placeid = obj1.getString("place_id");
            return placeid;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    public ArrayList<Reviews> getReviews(String data)
    {
        try
        {
            ArrayList<Reviews> reviews = new ArrayList<>();
            Reviews r;
            JSONObject obj = new JSONObject(data);



            JSONObject obj1 = obj.getJSONObject("result");
            JSONArray jarray = obj1.getJSONArray("reviews");

            for(int i =0;i<jarray.length();i++) {
                r=new Reviews();
                JSONObject obj2 = jarray.getJSONObject(i);
                if(obj2.has("author_name"))
                    r.setName(obj2.getString("author_name"));
                if(obj2.has("text"))
                    r.setReview(obj2.getString("text"));

                if(r.getName()!=null && r.getReview()!=null)
                    reviews.add(r);

            }
            return reviews;

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
