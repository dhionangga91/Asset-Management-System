package com.example.projectskripsi170101007.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectskripsi170101007.MainActivity;
import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.civil.AddWoCivil;
import com.example.projectskripsi170101007.civil.MyListWoCivil;
import com.example.projectskripsi170101007.electrical.MyListElectrical;
import com.example.projectskripsi170101007.electronic.MyListElectronic;
import com.example.projectskripsi170101007.hvac.MyListHvac;
import com.example.projectskripsi170101007.lift.MyListLift;
import com.example.projectskripsi170101007.model.Admin;
import com.example.projectskripsi170101007.plumbing.MyListPlumbing;
import com.example.projectskripsi170101007.sop.MainProcedure;
import com.example.projectskripsi170101007.splashscreen;
import com.example.projectskripsi170101007.ui.workorder.Workorder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView Akun;
    private String GetUserID;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private FloatingActionButton Sop,Hvac,Electrical,Plumbing,MainWorkorder,Civil,Electronic,Lift;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        Hvac = root.findViewById(R.id.btnhvac);
        Electrical = root.findViewById(R.id.btnelectrical);
        Plumbing = root.findViewById(R.id.btnplumbing);
        MainWorkorder = root.findViewById(R.id.btnwo);
        Sop = root.findViewById(R.id.btnsop);
        Civil = root.findViewById(R.id.btncivil);
        Electronic = root.findViewById(R.id.btnelectronic);
        Lift = root.findViewById(R.id.btnlift);
        Akun = root.findViewById(R.id.akun);
        getData();

        Electrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyListElectrical.class);
                startActivity(intent);
            }
        });

        Hvac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyListHvac.class);
                startActivity(intent);
            }
        });

        Plumbing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyListPlumbing.class);
                startActivity(intent);
            }
        });

        MainWorkorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Workorder.class);
                startActivity(intent);
            }
        });

        Sop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainProcedure.class);
                startActivity(intent);
            }
        });

        Electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyListElectronic.class);
                startActivity(intent);
            }
        });

        Civil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddWoCivil.class);
                startActivity(intent);
            }
        });

        Lift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyListLift.class);
                startActivity(intent);
            }
        });

        return root;
    }

    private void getData() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        GetUserID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("User").child(GetUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                Admin admin = snapshot.getValue(Admin.class);
                System.out.println(admin.getUsername());
                Akun.setText("Hi " + admin.getUsername() + " ,");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
}