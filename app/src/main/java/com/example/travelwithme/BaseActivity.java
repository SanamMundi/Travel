package com.example.travelwithme;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class BaseActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    String userRole="";
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        FirebaseApp.initializeApp(getApplicationContext());
        db = FirebaseFirestore.getInstance();

        fragmentManager = getSupportFragmentManager();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
      if(currentUser!=null)
        {

            DocumentReference docRef =  db.collection("Users").document(currentUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        userRole=document.get("role").toString();

                        if(userRole.equals("admin")) {
                            intent = new Intent(BaseActivity.this, AdminActivity.class);
                            Log.d("----", "admin logged in");
                            startActivity(intent);
                        }
                        else
                        {
                            intent = new Intent(BaseActivity.this, MainTravelActivity.class);
                            Log.d("----", "user logged in");
                            startActivity(intent);
                        }

                    } else {
                        Log.d("signin", "No such document");
                    }
                }
            });


        }
        else
        {

            loginFragment();

        }



    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



    public static void loginFragment()
    {

        fragmentManager.beginTransaction().replace(R.id.frameContainer,new SignInFragment()).commit();
    }
    public static void signUpFragment()
    {
        fragmentManager.beginTransaction().replace(R.id.frameContainer,new SignUpFragment()).commit();
    }







}
