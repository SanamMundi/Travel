package com.example.travelwithme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class ReviewsFragment extends Fragment {

    private ArrayList<Reviews> reviews;
    GetData data = new GetData();
    ReceiveData rd=new ReceiveData();
    Button writeReview;
    EditText et ;
    Reviews rv;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user;



    public ReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reviews, container, false);
        rv = new Reviews();
        reviews = new ArrayList<>();

        Bundle b = getArguments();
        String loc = b.getString("loc");
        String myData=data.getData("https://maps.googleapis.com/maps/api/geocode/json?address=" + loc + "&key=AIzaSyBYNwaeGibgmiD_43QTVQ4F-YkVkWeM00w");

        String placeid = rd.getPlaceId(myData);

        String myData2 = data.getData("https://maps.googleapis.com/maps/api/place/details/json?placeid="+placeid+"&key=AIzaSyBYNwaeGibgmiD_43QTVQ4F-YkVkWeM00w");
        reviews=rd.getReviews(myData2);

        et = (EditText)v.findViewById(R.id.write_review);
        writeReview = (Button)v.findViewById(R.id.reviewButton);
        user = auth.getCurrentUser();

        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rv.setName(user.getEmail());
                rv.setReview(et.getText().toString());

                reviews.add(rv);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new ReviewsFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });



        final RecyclerView rv =(RecyclerView)v.findViewById(R.id.myListRecylerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new MyListAdapter(getContext(),reviews));
        return v;
    }

}
