package com.example.travelwithme;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;



public class CityFragment extends Fragment {

    ArrayList<SectionalDataModel> sectionalDataModels;
    ReceiveData receiveData ;
    ArrayList<Restaurants> rest;


    public CityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_city, container, false);
        receiveData = new ReceiveData();
        rest = receiveData.receiveData("51.5074","-0.1278","restaurant");

        sectionalDataModels = new ArrayList<SectionalDataModel>();
        createDummyData(rest);

        RecyclerView my_recycler_view = (RecyclerView) v.findViewById(R.id.city_recycler);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getContext(), sectionalDataModels);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);

        return v;
    }

    public void createDummyData(ArrayList<Restaurants> rest) {
        for (int i = 1; i <= 5; i++) {

            SectionalDataModel dm = new SectionalDataModel();

            dm.setHeaderTitle("Section " + i);

            ArrayList<Destination> singleItem = new ArrayList<Destination>();
           for (int j = 0; j <= 5; j++) {
                singleItem.add(new Destination(rest.get(i)));
            }
         //singleItem.add(new Destination(rest));
        //   singleItem.

            dm.setDestinations(singleItem);
            sectionalDataModels.add(dm);

        }


    }
}
