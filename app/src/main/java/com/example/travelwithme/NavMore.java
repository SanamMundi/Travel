package com.example.travelwithme;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavMore extends Fragment {
    String[] nav = {"Home","Tours Near Me","Places To Go","My Reservations","Logout"};
    FirebaseAuth auth =  FirebaseAuth.getInstance();
    FirebaseUser user;
    Fragment fragment;
    TextView tv;



    public NavMore() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_nav_more, container, false);

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < nav.length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", nav[i]);

            aList.add(hm);
        }

        String[] from = {"listview_title"};
        int[] to = {R.id.text_row};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), aList, R.layout.listview_row, from, to);
        ListView androidListView = (ListView) v.findViewById(R.id.listView);
        androidListView.setAdapter(simpleAdapter);

        user = auth.getCurrentUser();
        String uDetails = user.getEmail();
        tv = (TextView)v.findViewById(R.id.user);
        tv.setText("Hey! " + uDetails);




        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(nav[position].equals("Home"))
                {
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                }
                if(nav[position].equals("Tours Near Me"))
                {
                    Bundle b = new Bundle();
                    b.putString("city","Vancouver");
                    fragment = new CityFragment();
                    fragment.setArguments(b);
                    loadFragment(fragment);
                }
                if(nav[position].equals("Places To Go"))
                {
                    MapsNearMeFragment map = new MapsNearMeFragment();
                    loadFragment(map);

                }
                if (nav[position].equals("My Reservations"))
                {

                }
                if(nav[position].equals("Logout"))
                {
                    logout();
                }
            }
        });


        return v;
    }

    public void loadFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void logout()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(),BaseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
