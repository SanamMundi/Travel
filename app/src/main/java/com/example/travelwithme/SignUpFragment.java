package com.example.travelwithme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;


public class SignUpFragment extends Fragment {
    private FirebaseAuth mAuth;
//    private DBConnection dbh;
    private EditText eTEmail, eTPassword;
    FirebaseFirestore db ;

    CollectionReference users;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_sign_up, container, false);
        mAuth = FirebaseAuth.getInstance();
        Button signUp = v.findViewById(R.id.buttonSignup);
        eTEmail = v.findViewById(R.id.eTSignUpEmail);
        eTPassword = v.findViewById(R.id.eTSignUpPassword);
        //dbh =  new DBConnection();
        //users = dbh.getCollectionReference("Users");

        db =  FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);





        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eTEmail.getText().toString();
                String password = eTPassword.getText().toString();

                if(email.equals("")||password.equals("")){
                    Toast.makeText(getActivity(), "Field/s empty", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();

                              
                                HashMap<String,String> data =  new HashMap<>();
                                data.put("email",user.getEmail());


                                if(user.getEmail()!="jasmine@gmail.com")
                                    data.put("role","user");
                                else
                                    data.put("role","admin");

                                db.collection("Users").document(user.getUid()).set(data);


                                startActivity(new Intent(getContext(), MainTravelActivity.class));
                            } else {
                                Toast.makeText(getContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        return v;
    }
}
