package com.example.travelwithme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment {

    FirebaseUser user;
    TextView tv;

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
        // Inflate the layout for this fragment
        FirebaseAuth auth =  FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String uDetails = user.getEmail();
        tv = (TextView)v.findViewById(R.id.usersName);
        tv.setText(uDetails);
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

}
