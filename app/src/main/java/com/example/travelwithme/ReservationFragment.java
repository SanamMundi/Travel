package com.example.travelwithme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.annotation.NonNull;

//import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment {

    FirebaseUser user;
    FirebaseAuth mAuth;
    TextView tv;
    FirebaseFirestore db;
    ArrayList<String> hotel;
    ArrayList<String> suites;
    ArrayList<String> pricesofrooms;

    //    private static FirebaseConnection connection = new FirebaseConnection();
//    private static CollectionReference inventories =
//            connection.getCollectionReference(CarInventory.CAR_INVENTORY_COLLECTION);
    public ReservationFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_reservation, container, false);


        hotel = new ArrayList<>();
        suites = new ArrayList<>();
        pricesofrooms = new ArrayList<>();


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        final List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setTimestampsInSnapshotsEnabled(true)
//                .build();
//        db.setFirestoreSettings(settings);
        // Inflate the layout for this fragment
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        db.collection("Users").document(user.getUid()).collection("Reservations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot d : task.getResult()) {
                        Log.d("all data", d.getId() + "456789" + d.getData());
                        hotel.add(d.getId());
                        suites.add(d.get("suite").toString());
                        pricesofrooms.add(d.get("price").toString());
                    }

                    String uDetails = user.getEmail();


                    Log.d("llllllllllllll", hotel.size() + "");

                    for (int i = 0; i < hotel.size(); i++) {
                        final HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("hotel", hotel.get(i));
                        aList.add(hm);
                        Log.d("hotelllll", hotel.get(i) + "");
                    }

                    String[] from = {"hotel"};
                    int[] to = {R.id.txtHotel};

                    SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), aList, R.layout.reservation_list, from, to);
                    ListView androidListView = (ListView) v.findViewById(R.id.reservationList);
                    androidListView.setAdapter(simpleAdapter);


                    androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            HotelReviewsFragment hr = new HotelReviewsFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("hotelName", hotel.get(position));
                            bundle.putString("suites", suites.get(position));
                            bundle.putString("prices", pricesofrooms.get(position));
                            hr.setArguments(bundle);


                            Toast.makeText(getContext(), hotel.get(position) + "dsf" + suites.get(position) + pricesofrooms.get(position), Toast.LENGTH_SHORT).show();
                            /*
                             Bundle args = new Bundle();
                args.putDouble("lat", Double.parseDouble(myCity.getLat()));
                args.putDouble("lng", Double.parseDouble(myCity.getLng()));
                args.putString("name", city);
                mf.setArguments(args);
                             */


                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, hr);
                            transaction.addToBackStack(null);
                            transaction.commit();

                        }
                    });


                    db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot d : task.getResult()) {
                                    Log.d("something", d.getId() + d.getData());
                                }
                            } else {
                                Log.d("da", "data not read");
                            }
                        }
                    });


                    for (int i = 0; i < hotel.size(); i++) {
                        Log.d("hotellist", hotel.get(i));
                    }
                } else {
                    Log.d("error", "data not read");
                }
            }
        });

//        readReservations(user.getUid());


        return v;
    }


    /*

    public static void getAllCars(String inventoryName){

        CollectionReference cars = inventories.document(inventoryName)
                .collection(CarInventory.CAR_COLLECTION);

        cars.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Car currCar = document.toObject(Car.class);
                        Log.d("getAllCars", document.getId() + " => " + currCar);
                    }
                } else {
                    Log.d("getAllCars", "Error getting documents: ", task.getException());
                }
            }
        });
    }
     */


    public void readReservations(String id) {


    }


}
