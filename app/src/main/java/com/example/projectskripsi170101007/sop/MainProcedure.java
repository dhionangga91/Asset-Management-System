package com.example.projectskripsi170101007.sop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projectskripsi170101007.R;

public class MainProcedure extends AppCompatActivity {

    private Button Electrical,Hvac,Plumbing,Electronic,Lift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_procedure);

        Electrical = findViewById(R.id.electrical);
        Hvac = findViewById(R.id.hvac);
        Plumbing = findViewById(R.id.plumbing);
        Electronic = findViewById(R.id.electronic);
        Lift = findViewById(R.id.lift);

        Electrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainProcedure.this,ElectricalProcedure.class);
                startActivity(intent);
            }
        });

        Electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainProcedure.this,ElectronicProcedure.class);
                startActivity(intent);
            }
        });

        Hvac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainProcedure.this,HvacProcedure.class);
                startActivity(intent);
            }
        });

        Plumbing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainProcedure.this,PlumbingProcedure.class);
                startActivity(intent);
            }
        });

        Lift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainProcedure.this,LiftProcedure.class);
                startActivity(intent);
            }
        });
    }
}