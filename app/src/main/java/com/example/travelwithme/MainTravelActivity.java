package com.example.travelwithme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainTravelActivity extends AppCompatActivity {

        private ActionBar toolbar;
        MapsNearMeFragment map = new MapsNearMeFragment();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_travel);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        loadFragment(new HomeFragment());




        toolbar = getSupportActionBar();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch(item.getItemId())
                {

                    case(R.id.navigation_main):
                        toolbar.setTitle("Home");
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case(R.id.navigation_maps):
                        toolbar.setTitle("Maps");
                        loadFragment(map);
                        return true;
                    case(R.id.navigation_tours):
                        toolbar.setTitle("Tours");
                        return true;
                    case(R.id.navigation_logout):
                        logout();
                        return true;

                }
                return false;
            }
        });

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigatorBehavior());


    }

    private void loadFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void logout()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(),BaseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == MapsNearMeFragment.MY_PERMISSIONS_REQUEST_LOCATION){
            map.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void other(double lat, double lng){


        //String lat="49.24";
        //String lng="-123.1183";


        String category="cat=hotel";
        //String category="cat=car_rental";
        String site = "https://places.demo.api.here.com/places/v1/discover/explore?";
        String language="Accept-Language=en-US,en";
        String app_id="DemoAppId01082013GAL";
        String app_code="AJKnXv84fjrb0KIHawS0Tg";
        final String url = String.format("%sat=%s,%s&%s&%s;&app_id=%s&app_code=%s",site,lat,lng,category,language,app_id,app_code);


       // GetNearby getNearby = new GetNearby(MainTravelActivity.this);
        //getNearby.execute(url);
    }




 //   public ArrayList<LocationData> getHotelsList(){
   ///     return locationList;
    //}

    //public void setHotelsList(ArrayList<LocationData> locationList){
      //  this.locationList = locationList;
   // }
}
