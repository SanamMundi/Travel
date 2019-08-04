package com.example.travelwithme;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainTravelActivity extends AppCompatActivity {

        private Toolbar toolbar;
        MapsNearMeFragment map = new MapsNearMeFragment();

    String[] option = {"My Reservations","Logout"};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_travel);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        loadFragment(new HomeFragment());








        final BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                Fragment fragment;
                switch(item.getItemId())
                {

                    case(R.id.navigation_main):

                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case(R.id.navigation_maps):

                        loadFragment(map);
                        return true;
                    case(R.id.navigation_tours):

                        Bundle b = new Bundle();
                        b.putString("city","Vancouver");
                        fragment = new CityFragment();
                        fragment.setArguments(b);
                        loadFragment(fragment);
                        return true;
                    case(R.id.navigation):
                        fragment = new NavMore();
                        loadFragment(fragment);

                        return true;

                }
                return false;
            }
        });

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigatorBehavior());


    }



    public void loadFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

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
