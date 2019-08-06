package com.example.travelwithme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import androidx.annotation.NonNull;

//import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment {

    FirebaseUser user;
    FirebaseAuth mAuth;
    TextView tv;
    FirebaseFirestore db;

//    private static FirebaseConnection connection = new FirebaseConnection();
//    private static CollectionReference inventories =
//            connection.getCollectionReference(CarInventory.CAR_INVENTORY_COLLECTION);
    public ReservationFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reservation, container, false);

        mAuth = FirebaseAuth.getInstance();
        db =  FirebaseFirestore.getInstance();
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setTimestampsInSnapshotsEnabled(true)
//                .build();
//        db.setFirestoreSettings(settings);
        // Inflate the layout for this fragment
        FirebaseAuth auth =  FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String uDetails = user.getEmail();
        tv = (TextView)v.findViewById(R.id.usersName);
        tv.setText(uDetails);



        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot d: task.getResult()){
                        Log.d("something", d.getId() + d.getData() );
                    }
                }else{
                    Log.d("da", "data not read");
                }
            }
        });



        readReservations(user.getUid());



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


    public void readReservations(String id){

        db.collection("Users").document(id).collection("Reservations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete( Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot d: task.getResult()){
                        Log.d("all data", d.getId() + "456789" + d.getData());
                    }
                }else{
                    Log.d("error", "data not read");
                }
            }
        });
    }


}
