package com.example.travelwithme;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CityFragment extends Fragment {

    ArrayList<SectionalDataModel> sectionalDataModels;
    ReceiveData receiveData;
    ArrayList<Data> data;
    ArrayList<ArrayList<Data>> myData = new ArrayList<>();
    final String[] types = {"bakery", "restaurants", "park", "supermarket"};
    private GetData cityData;
    private City myCity;

    TextView cityName;

    MainTravelActivity mt = new MainTravelActivity();



    public CityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_city, container, false);
        cityName = (TextView) v.findViewById(R.id.city_name);
        receiveData = new ReceiveData();
        Bundle b = getArguments();
        final String city = b.getString("city");




        cityName.setText(city);

        cityData = new GetData();
        receiveData = new ReceiveData();

        String locData;

        locData = cityData.getData("https://maps.googleapis.com/maps/api/geocode/json?address=" + city + "&key=AIzaSyBYNwaeGibgmiD_43QTVQ4F-YkVkWeM00w");
        myCity = receiveData.cityData(locData);
        Log.d("Dfasd", myCity.getLat());


        for (int i = 0; i < types.length; i++) {
            data = receiveData.receiveData(myCity.getLat(), myCity.getLng(), types[i]);
            myData.add(data);

        }


        ImageButton ib = (ImageButton) v.findViewById(R.id.map_button);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment mf = new MapFragment();
                Bundle args = new Bundle();
                args.putDouble("lat", Double.parseDouble(myCity.getLat()));
                args.putDouble("lng", Double.parseDouble(myCity.getLng()));
                args.putString("name", city);
                mf.setArguments(args);
                Log.d("Dfasddfasdf", myCity.getLat());



                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, mf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        sectionalDataModels = new ArrayList<SectionalDataModel>();
        createDummyData(myData);

        RecyclerView my_recycler_view = (RecyclerView) v.findViewById(R.id.city_recycler);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getContext(), sectionalDataModels);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);

        return v;
    }

    public void createDummyData(ArrayList<ArrayList<Data>> data) {
        for (int i = 1; i < 5; i++) {

            SectionalDataModel dm = new SectionalDataModel();

            String[] headers = {"", "Restaurants", "Hotels", "Tours", "Places of Interest"};

            dm.setHeaderTitle(headers[i]);

            ArrayList<Destination> singleItem = new ArrayList<Destination>();

//            if(i==1){
//                for(int a=0; a<rest.size(); a++){
//
//                }
//
//            }
            //singleItem.add(new Destination(rest.get(i)));
            if (i == 1) {
                for (int j = 1; j <= 6; j++) {

                    singleItem.add(new Destination(data.get(0).get(j)));
                }
            }
            if (i == 2) {
                for (int j = 1; j <= 6; j++) {

                    singleItem.add(new Destination(data.get(1).get(j)));
                }

            }
            if (i == 3) {
                for (int j = 1; j <= 6; j++) {

                    singleItem.add(new Destination(data.get(2).get(j)));
                }

            }
            if (i == 4) {
                for (int j = 1; j <= 6; j++) {

                    singleItem.add(new Destination(data.get(3).get(j)));
                }

            }
            //singleItem.add(new Destination(rest));
            //   singleItem.
            dm.setDestinations(singleItem);

            sectionalDataModels.add(dm);

        }


    }
}
