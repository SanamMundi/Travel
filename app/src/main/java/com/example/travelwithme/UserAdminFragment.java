package com.example.travelwithme;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserAdminFragment extends Fragment {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    View v;
    ListView androidListView ;



    public UserAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_user_admin, container, false);
        final List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        androidListView= (ListView) v.findViewById(R.id.adminUserList);


        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("user", document.get("email").toString());
                                hm.put("role", document.get("role").toString());
                                aList.add(hm);

                            }

                            String[] from = {"user","role"};
                            int[] to = {R.id.UserEmail,R.id.userRole};

                            SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), aList, R.layout.admin_list_view, from, to);

                            androidListView.setAdapter(simpleAdapter);



                        } else {
                            Log.d("Data", "Error getting documents: ", task.getException());
                        }
                    }
                });



                Button btnDel = (Button)v.findViewById(R.id.btnDel);
                btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                        transaction.replace(R.id.adminContainer,new DeleteUserFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });









        return v;
    }

}
