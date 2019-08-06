package com.example.travelwithme;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserReviewFragment extends Fragment {

    FirebaseFirestore db;
    View v;
    ArrayList<String> hotelName;
    ArrayList<String> userEmail;
    ArrayList<String> userReview;



    public UserReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =inflater.inflate(R.layout.fragment_user_review, container, false);
        db =  FirebaseFirestore.getInstance();
        hotelName =  new ArrayList<>();
        userEmail =  new ArrayList<>();
        userReview =  new ArrayList<>();
        final List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();




        db.collection("Reviews").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot d: task.getResult()){
                        hotelName.add(d.getId());
                        userEmail.add(d.get("uemail").toString());
                        userReview.add(d.get("review").toString());
                        Log.d("123456", d.getId() + "==> " + d.getData());
                    }


                    for(int i =0;i<hotelName.size();i++)
                    {
                        final HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("hotel","Hotel Name: " +hotelName.get(i));
                        hm.put("email","User Email: "+userEmail.get(i));
                        hm.put("review","User Review: "+userReview.get(i));

                        aList.add(hm);
                    }

                    String[] from = {"hotel","email","review"};
                    int[] to = {R.id.txtHotelName,R.id.txtUserName,R.id.txtReview};

                    SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), aList, R.layout.review_list, from, to);
                    ListView androidListView = (ListView) v.findViewById(R.id.userReviewList);
                    androidListView.setAdapter(simpleAdapter);
                }else{
                    Log.d("error", "can't read data");

                }
            }
        });


        return v;
    }

}
