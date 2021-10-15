package com.example.projectskripsi170101007.lift;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.electrical.AddHistoryPmElectrical;
import com.example.projectskripsi170101007.model.Admin;
import com.example.projectskripsi170101007.model.ModelHistoricalPmLift;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.text.TextUtils.isEmpty;

public class AddHistoryPmLift extends AppCompatActivity {

    private TextView MaintenanceDate,Pic,Remarks,Name,Status,CurrentUser;
    private FloatingActionButton Submit;
    private String AuthAdmin = "supervisor@astra.com" , GetUserID ;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_history_pm_lift);

        MaintenanceDate = findViewById(R.id.maintenancedate);
        Status = findViewById(R.id.status);
        Pic = findViewById(R.id.pic);
        Remarks = findViewById(R.id.remarks);
        Name = findViewById(R.id.name);
        CurrentUser = findViewById(R.id.authadmin);
        Submit = findViewById(R.id.save);
        mDatabase = FirebaseDatabase.getInstance().getReference("Aset/Workorder/lift");
        GetData();
        GetAdmin();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getRemarks = Remarks.getText().toString();
                String getPic = Pic.getText().toString();
                String getMaintenanceDate = MaintenanceDate.getText().toString();

                if ( isEmpty(getRemarks) || isEmpty(getPic) || isEmpty(getMaintenanceDate) ) {
                    //Jika Ada, maka akan menampilkan pesan singkan seperti berikut ini.
                    Toast.makeText(AddHistoryPmLift.this, "Incomplete Data", Toast.LENGTH_SHORT).show();
                } else {
                    cekKoneksi();

                }

            }
        });
    }

    private void cekKoneksi() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            cekStatus();

        }else{
            Toast.makeText(getApplicationContext(),"Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void cekStatus() {
        if (Status.getText().toString().equals("Approved")){
            uploadData();
        }else {
            Toast.makeText(AddHistoryPmLift.this,"Workorder not approved yet",Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadData() {
        if( (CurrentUser.getText().toString().equals(AuthAdmin))  && mDatabase != null) {
            mDatabase.child("Aset").child("History").child("Lift").child(Name.getText().toString()).push().setValue(new ModelHistoricalPmLift(
                    Pic.getText().toString(),
                    MaintenanceDate.getText().toString(),
                    Remarks.getText().toString()

            ));
            Toast.makeText(AddHistoryPmLift.this, "Uploading Success", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(AddHistoryPmLift.this, MyListLift.class);
            startActivity(i);
        } else {
            Toast.makeText(AddHistoryPmLift.this, "Insufficient Access Level", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void GetAdmin() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        GetUserID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("User").child(GetUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                com.example.projectskripsi170101007.model.Admin admin = snapshot.getValue(Admin.class);
                System.out.println(admin.getEmail());
                CurrentUser.setText(admin.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    private void GetData() {

        final String getName = getIntent().getExtras().getString("dataName");
        final String getStatus = getIntent().getExtras().getString("dataStatus");
        final String getPic = getIntent().getExtras().getString("dataPic");
        final String getMaintenanceDate = getIntent().getExtras().getString("dataFinishDate");
        final String getRemarks = getIntent().getExtras().getString("dataRemarks");
        Name.setText(getName);
        Status.setText(getStatus);
        Pic.setText(getPic);
        MaintenanceDate.setText(getMaintenanceDate);
        Remarks.setText(getRemarks);

    }

}