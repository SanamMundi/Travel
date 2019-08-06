package com.example.travelwithme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.annotation.NonNull;

//import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class HotelFragment extends Fragment {


//    String[] roomTypes = {"Single", "Double", "Executive", "Suite"};
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    ArrayList<String> room = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();

    public HotelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_hotel, container, false);

        db =  FirebaseFirestore.getInstance();
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setTimestampsInSnapshotsEnabled(true)
//                .build();
//        db.setFirestoreSettings(settings);


        mAuth = FirebaseAuth.getInstance();




        Random rand = new Random();
        int n = rand.nextInt(5);
        String[] hotels = {"first", "second", "third", "fourth", "fifth", "sixth"};



        db.collection("Hotels").document("Rooms")
                .collection(hotels[n]).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete( Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        Log.d("gettingall", documentSnapshot.getId() + "====> " +documentSnapshot.get("price"));
                        room.add(documentSnapshot.getId());
                        prices.add(documentSnapshot.get("price").toString());
                    }


                    final Bundle aBundle = getArguments();
                    final String hotelName = aBundle.getString("hotelName");

                    TextView d = (TextView)v.findViewById(R.id.doubleTextView);
                    TextView e = (TextView)v.findViewById(R.id.exTextView);
                    TextView s = (TextView)v.findViewById(R.id.singleTextView);
                    TextView three = (TextView)v.findViewById(R.id.threeTextView);

                    d.setText(room.get(0).toUpperCase() +  " ROOM");
                    e.setText(room.get(1).toUpperCase() + " SUITE");
                    s.setText(room.get(2).toUpperCase() + " ROOM");
                    three.setText("3 BED" + room.get(3).toUpperCase());

                    TextView dPrice = (TextView)v.findViewById(R.id.doublePriceTextView);
                    TextView ePrice = (TextView)v.findViewById(R.id.exPriceTextView);
                    TextView sPrice = (TextView)v.findViewById(R.id.singlePriceTextView);
                    TextView threePrice = (TextView)v.findViewById(R.id.threePriceTextView);

                    dPrice.setText(prices.get(0));
                    ePrice.setText(prices.get(1));
                    sPrice.setText(prices.get(2));
                    threePrice.setText(prices.get(3));


                    Button dButton = (Button)v.findViewById(R.id.doubleButton);
                    Button eButton = (Button)v.findViewById(R.id.exButton);
                    Button sButton = (Button)v.findViewById(R.id.singleButton);
                    Button threeButton = (Button)v.findViewById(R.id.threeButton);

                    dButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adding(room.get(0).toString(), prices.get(0).toString(), hotelName);
                        }
                    });
                    eButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("hello", "hellocheck");
                            adding(room.get(1).toString(), prices.get(1).toString(), hotelName);
                        }
                    });
                    sButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adding(room.get(2).toString(), prices.get(2).toString(), hotelName);
                        }
                    });
                    threeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adding(room.get(3).toString(), prices.get(3).toString(), hotelName);
                        }
                    });




//                    Log.d("sizeofarraylist", room.size() + " " + prices.size());
//                    Log.d("asdfgh", room.get(1) + prices.get(1));
//
//
//                    String[] price = new String[prices.size()];
//                    String[] roomTypes = new String[room.size()];
//                    for(int a=0; a<price.length; a++){
//                        price[a] = prices.get(a);
//                    }
//
//                    for(int b=0; b<roomTypes.length; b++){
//                        roomTypes[b] = room.get(b);
//                    }
//
//
//                    List<HashMap<String, String>> list= new ArrayList<HashMap<String, String>>();
//
//                    /////////////add length of room types///////////
//                    for(int a=0; a<roomTypes.length; a++){
//                        HashMap<String, String> hm = new HashMap<String, String>();
//                        if(roomTypes[a].equals("executive")){
//                            hm.put("listview_title", roomTypes[a].toUpperCase() + " SUITE");
//                        }else if(roomTypes[a].equals("suite")){
//                            hm.put("listview_title", "3 BED " + roomTypes[a].toUpperCase());
//                        }else{
//                            hm.put("listview_title", roomTypes[a].toUpperCase() + " ROOMS");
//                        }
//                        hm.put("prices", price[a] + " /day");
//                        list.add(hm);
//                    }
//
//                    String[] from = {"listview_title", "prices"};
//                    int[] to = {R.id.roomType, R.id.price};
//
//                    Button add = (Button)v.findViewById(R.id.addButton);
//                    add.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), list, R.layout.hotel_list, from ,to);
//                    ListView a = (ListView) v.findViewById(R.id.hotelListView);
//                    a.setAdapter(simpleAdapter);




//                    Button addButton = (Button)v.findViewById(R.id.addButton);
//
//
//
//                    addButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(getContext(), "he", Toast.LENGTH_SHORT ).show();
//                        }
//                    });


//                    int photo_id = Integer.parseInt(aBundle.getString("photo"));

                    //"https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photo_id + "&key=AIzaSyBYNwaeGibgmiD_43QTVQ4F-YkVkWeM00w"

//                    ImageView iv = (ImageView)v.findViewById(R.id.hotelImage);


                    TextView textView = (TextView) v.findViewById(R.id.hotelName);
                    textView.setText(hotelName);
                }else{
                    Log.d("getting all", "Error getting");
                }
            }
        });





//        Button reserveButton = (Button)v.findViewById(R.id.reserveButton);
//        reserveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("done", "Booking done");
//            }
//        });
        return v;
    }


    public void adding(String type, String price, String hotel){
        FirebaseUser user = mAuth.getCurrentUser();

        Map<String, String> data = new HashMap<>();
        data.put("suite", type);
        data.put("price", price);

        db.collection("Users").document(user.getUid())
                .collection("Reservations").document(hotel)
                .set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Reservation done", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) {
                Toast.makeText(getContext(), "Reservation not done", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
