package com.example.travelwithme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainTravelActivity extends AppCompatActivity {
    private ActionBar toolbar;
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_travel);


        fragment =new HomeFragment();
        loadFragment(fragment);


        toolbar = getSupportActionBar();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case(R.id.navigation_main):
                        toolbar.setTitle("Home");
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case(R.id.navigation_maps):
                        toolbar.setTitle("Maps");
                        return true;
                    case(R.id.navigation_tours):
                        toolbar.setTitle("Tours");
                        return true;
                    case(R.id.navigation_logout):
                        logout();
                        return true;

                }
                return false;
            }
        });


    }

    private void loadFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void logout()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(),BaseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
