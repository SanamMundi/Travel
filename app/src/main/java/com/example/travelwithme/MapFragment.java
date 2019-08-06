package com.example.travelwithme;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.map);
        if(mMapView!=null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        Bundle bs = getArguments();
        Double latitude = bs.getDouble("lat");
        Double longitude = bs.getDouble("lng");
        String cityName = bs.getString("name");

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
        hotelData = rd.receiveData(latitude+"", longitude+"", hotels);
        restData = rd.receiveData(latitude+"", longitude+"", restaurants);
//        d = rd.receiveData(vanLat.toString(), vanLng.toString(), hotels);

        for(int a=0; a<hotelData.size(); a++){
            hlatitudesList.add(hotelData.get(a).getLat());
            hlongitudesList.add(hotelData.get(a).getLng());
            hnamesList.add(hotelData.get(a).getName());

        }

        for(int b = 0; b<restData.size(); b++){
            rlatitudesList.add(restData.get(b).getLat());
            rlongitudesList.add(restData.get(b).getLng());
            rnamesList.add(restData.get(b).getName());
        }

        MarkerOptions m = new MarkerOptions();
        m.position(new LatLng(latitude, longitude));
        m.title(cityName);
        m.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mGoogleMap.addMarker(m);

//        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(cityName).icon(BitmapDescriptorFactory.defaultMarker()(BitmapDescriptorFactory.HUE_MAGENTA)));




        for(int i=0; i<hotelData.size(); i++){
            LatLng l = new LatLng(Double.parseDouble(hlatitudesList.get(i)) , Double.parseDouble(hlongitudesList.get(i)));
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(l);
            markerOption.title(hnamesList.get(i));
            markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(markerOption);
        }

        for(int i=0; i<restData.size(); i++){
            LatLng l = new LatLng(Double.parseDouble(rlatitudesList.get(i)) , Double.parseDouble(rlongitudesList.get(i)));
            MarkerOptions markerOption1 = new MarkerOptions();
            markerOption1.position(l);
            markerOption1.title(rnamesList.get(i));
            markerOption1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            mGoogleMap.addMarker(markerOption1);
        }

        CameraPosition l = CameraPosition.builder().target(new LatLng(latitude, longitude)).zoom(14).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(l));
    }
}
