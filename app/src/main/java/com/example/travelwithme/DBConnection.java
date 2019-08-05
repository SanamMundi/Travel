package com.example.travelwithme;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DBConnection {

    private FirebaseFirestore db;

    public DBConnection() {
        db = FirebaseFirestore.getInstance();
    }

    public CollectionReference getCollectionReference(String collectionName) {
        return db.collection(collectionName);
    }
}
