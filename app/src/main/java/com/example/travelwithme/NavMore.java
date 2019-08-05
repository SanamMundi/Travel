package com.example.travelwithme;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavMore extends Fragment {
    String[] nav = {"Home","Tours Near Me","Places To Go","My Reservations","Logout",""};
    FirebaseAuth auth =  FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView img ;
    FirebaseUser user;
    Fragment fragment;
    TextView tv;
    String userRole="";
    View v;



    public NavMore() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_nav_more, container, false);
        img = (ImageView)v.findViewById(R.id.imageTravel);

        user = auth.getCurrentUser();

        DocumentReference docRef =  db.collection("Users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    userRole=document.get("role")+"";
                    if(userRole.equals("admin")) {
                        nav[nav.length - 1] = "Go to Admin page";
                        img.getLayoutParams().height = 500;
                    }

                    List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

                    for (int i = 0; i < nav.length; i++) {
                        HashMap<String, String> hm = new HashMap<String, String>();
                        if(!nav[i].equals(""))
                            hm.put("listview_title", nav[i]);

                        aList.add(hm);
                    }

                    String[] from = {"listview_title"};
                    int[] to = {R.id.text_row};

                    SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), aList, R.layout.listview_row, from, to);
                    ListView androidListView = (ListView) v.findViewById(R.id.listView);
                    androidListView.setAdapter(simpleAdapter);


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
                                ReservationFragment rf = new ReservationFragment();
                                loadFragment(rf);
                            }
                            if(nav[position].equals("Logout"))
                            {
                                logout();
                            }
                            if(nav[position].equals("Go to Admin page"))
                            {
                                Intent intent = new Intent(getContext(),AdminActivity.class);
                                startActivity(intent);

                            }
                        }
                    });



                } else {
                    Log.d("signin", "No such document");
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
