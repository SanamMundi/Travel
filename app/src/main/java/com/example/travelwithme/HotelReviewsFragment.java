package com.example.travelwithme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HotelReviewsFragment extends Fragment {


    FirebaseFirestore db;
    FirebaseAuth mAuth;
    public HotelReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_hotel_reviews, container, false);



        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        Bundle a = getArguments();
        final String hotName = a.getString("hotelName");
        String suite = a.getString("suites");
        String prices = a.getString("prices");


        Log.d("alll", hotName + suite + prices);

        TextView tv = (TextView)v.findViewById(R.id.hotelNames);
        tv.setText(hotName);

        TextView suites = (TextView)v.findViewById(R.id.suiteValueTextView);
        suites.setText(suite);

        TextView pri = (TextView)v.findViewById(R.id.priceValueTextView);
        pri.setText(prices);

        final EditText et = (EditText)v.findViewById(R.id.addReviewEditText);

/*
FirebaseUser user = mAuth.getCurrentUser();

        Map<String, String> data = new HashMap<>();
        data.put("suite", type);
        data.put("price", price);
 */



        Button addReview = (Button)v.findViewById(R.id.addReview);

        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String text = et.getText().toString().trim();
                if(text.equals("")){
                    Toast.makeText(getContext(), "Empty Text Field", Toast.LENGTH_SHORT).show();
                }else{

                    FirebaseUser user = mAuth.getCurrentUser();

                    Map<String, String> data = new HashMap<>();
                    data.put("uemail", user.getEmail());
                    data.put("review", text);

                    db.collection("Reviews").document(hotName).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Review Added", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(getContext(), "Can't add review", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });








        return v;
    }

}
