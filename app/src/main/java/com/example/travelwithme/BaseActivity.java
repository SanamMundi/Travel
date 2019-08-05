package com.example.travelwithme;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class BaseActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        FirebaseApp.initializeApp(getApplicationContext());

        fragmentManager = getSupportFragmentManager();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
      if(currentUser!=null)
        {
            Intent intent = new Intent( BaseActivity.this, MainTravelActivity.class);
            Log.d("----","user logged in");
            startActivity(intent);
        }
        else
        {

            loginFragment();

        }

        loginFragment();

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
