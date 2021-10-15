package com.example.projectskripsi170101007;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPassword extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText inputEmail;
    private Button Back, Reset;
    private ProgressBar ProgressBar;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputEmail = findViewById(R.id.email);
        Reset = findViewById(R.id.btn_reset_password);
        Back = findViewById(R.id.btn_back);
        ProgressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cekKoneksi();
            }
        });

//        Reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cekDataUser();
//
//            }
//        });
    }

    private void cekKoneksi() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            cekDataUser();

        }else{
            Toast.makeText(getApplicationContext(),"Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void cekDataUser() {
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Aset").child("Admin");
        String email = inputEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(ForgotPassword.this, "Enter your registered email", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgotPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }

                        ProgressBar.setVisibility(View.GONE);
                    }
                });
//        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("User");
//        ref.orderByChild("email").equalTo(inputEmail.getText().toString()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot){
//                if(dataSnapshot.exists()) {
//                    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
//                    mDatabase.child("Aset").child("Admin");
//                    String email = inputEmail.getText().toString().trim();
//
//                    if (TextUtils.isEmpty(email)) {
//                        Toast.makeText(getApplication(), "Enter your registered email", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    ProgressBar.setVisibility(View.VISIBLE);
//                    auth.sendPasswordResetEmail(email)
//                            .addOnCompleteListener(new OnCompleteListener() {
//                                @Override
//                                public void onComplete(@NonNull Task task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(ForgotPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(ForgotPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    ProgressBar.setVisibility(View.GONE);
//                                }
//                            });
//
//                }
//                else {
//                    Toast.makeText(ForgotPassword.this, "Email has not been registered", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
    }

}
