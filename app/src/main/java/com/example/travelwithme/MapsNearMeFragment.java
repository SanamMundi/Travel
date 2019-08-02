package com.example.travelwithme;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import com.google.android.gms.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class MapsNearMeFragment extends SupportMapFragment implements GetNearby.getNearbyListener,

        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    ArrayList<LocationData> locationList = new ArrayList<>();





    @Override
    public void onResume() {
        super.onResume();

        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if (mGoogleMap == null) {
            getMapAsync(this);
        }
    }
    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        //mLocationRequest.setInterval(1000);
        //mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }


        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        Double vanLat = 49.2827;
        Double vanLng = -123.1207;

        ReceiveData rd = new ReceiveData();
        ArrayList<Data> hotelData =  new ArrayList<>();
        ArrayList<Data> restData = new ArrayList<>();



        String hotels = "hotels";
        String restaurants = "restaurant";

        ArrayList<String> hlatitudesList = new ArrayList<>();
        ArrayList<String> hlongitudesList = new ArrayList<>();
        ArrayList<String> hnamesList = new ArrayList<>();

        ArrayList<String> rlatitudesList = new ArrayList<>();
        ArrayList<String> rlongitudesList = new ArrayList<>();
        ArrayList<String> rnamesList = new ArrayList<>();
        //////////////////////////////////////////
        hotelData = rd.receiveData(location.getLatitude()+"", location.getLongitude()+"", hotels);
        restData = rd.receiveData(location.getLatitude()+"", location.getLongitude()+"", restaurants);
//        d = rd.receiveData(vanLat.toString(), vanLng.toString(), hotels);

        for(int a=0; a<hotelData.size(); a++){
            hlatitudesList.add(hotelData.get(a).getLat());
            hlongitudesList.add(hotelData.get(a).getLng());
            hnamesList.add(hotelData.get(a).getName());
            Log.d("hotels-----", hotelData.get(a). getName()
                    + hotelData.get(a).getVicinity() + hotelData.get(a).getLat()
                    + hotelData.get(a).getLng());
        }

        for(int b = 0; b<restData.size(); b++){
            rlatitudesList.add(restData.get(b).getLat());
            rlongitudesList.add(restData.get(b).getLng());
            rnamesList.add(restData.get(b).getName());
            Log.d("restaurants----", restData.get(b). getName()
                    + restData.get(b).getVicinity() + restData.get(b).getLat()
                    + restData.get(b).getLng());
        }


//        LatLng vL = new LatLng(vanLat, vanLng);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        for(int i=0; i<hotelData.size(); i++){
            LatLng l = new LatLng(Double.parseDouble(hlatitudesList.get(i)) , Double.parseDouble(hlongitudesList.get(i)));
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(l);
            markerOption.title(hnamesList.get(i));
            markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mCurrLocationMarker = mGoogleMap.addMarker(markerOption);
        }

        for(int i=0; i<restData.size(); i++){
            LatLng l = new LatLng(Double.parseDouble(rlatitudesList.get(i)) , Double.parseDouble(rlongitudesList.get(i)));
            MarkerOptions markerOption1 = new MarkerOptions();
            markerOption1.position(l);
            markerOption1.title(rnamesList.get(i));
            markerOption1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            mCurrLocationMarker = mGoogleMap.addMarker(markerOption1);
        }






//        MainTravelActivity mt = new MainTravelActivity();
        //mt.other(location.getLatitude(), location.getLongitude());


        //Log.d("list of data", );


        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));

    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;



    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public ArrayList getResult(String jsonData) {
        Log.d("inside", jsonData);


        try{
            JSONObject jo = new JSONObject(jsonData);
            JSONArray jArray = jo.getJSONObject("results").getJSONArray("items");
            JSONObject itemData;
            String lat, lng;
            String title;
            String category;
            String icon;
            String address;


            for(int i=0; i<jArray.length(); i++){



                itemData = jArray.getJSONObject(i);
                lat = itemData.getJSONArray("position").getString(0);
                lng = itemData.getJSONArray("position").getString(1);
                title = itemData.getString("title");
                category = itemData.getJSONObject("category").getString("id");
                icon = itemData.getString("icon");
                address = itemData.getString("vicinity");
                locationList.add(new LocationData(lat, title, category, address, icon));
                //locationList.add(new Location(lat+ "," + lng,title,category));
                //locationList.
                Log.d("afdasfasdfasdfasdfasdf", lat +",,,,"+ lng + title + category + address + icon);
            }


        }catch(JSONException e){
            e.printStackTrace();
        }

        return null;
    }

}