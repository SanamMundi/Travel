package com.example.travelwithme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HotelFragment extends Fragment {


    String[] roomTypes = {"Single", "Double", "Executive", "Suite"};
    Double[] price = {300.00, 400.00, 200.00, 680.00};

    public HotelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hotel, container, false);

        List<HashMap<String, String>> list= new ArrayList<HashMap<String, String>>();

        ///////////////add length of room types///////////
        for(int a=0; a<roomTypes.length; a++){
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", roomTypes[a]);
            hm.put("prices", price[a].toString());
            list.add(hm);
        }

        String[] from = {"listview_title", "prices"};
        int[] to = {R.id.roomType, R.id.price};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), list, R.layout.hotel_list, from ,to);
        ListView a = (ListView) v.findViewById(R.id.hotelListView);
        a.setAdapter(simpleAdapter);

        Bundle aBundle = getArguments();
        String hotelName = aBundle.getString("hotelName");

        TextView textView = (TextView) v.findViewById(R.id.hotelName);
        textView.setText(hotelName);

        Button reserveButton = (Button)v.findViewById(R.id.reserveButton);
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("done", "Booking done");
            }
        });
        return v;
    }

}
