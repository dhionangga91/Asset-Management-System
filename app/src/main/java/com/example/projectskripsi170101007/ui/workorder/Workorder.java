package com.example.projectskripsi170101007.ui.workorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.civil.HistoryListPmCivil;
import com.example.projectskripsi170101007.civil.MyListWoCivil;
import com.example.projectskripsi170101007.electrical.MyListWoElectrical;
import com.example.projectskripsi170101007.electronic.MyListWoElectronic;
import com.example.projectskripsi170101007.hvac.MyListWoHvac;
import com.example.projectskripsi170101007.lift.MyListWoLift;
import com.example.projectskripsi170101007.plumbing.MyListWoPlumbing;

public class Workorder extends AppCompatActivity {

    private Button Electrical,Hvac,Plumbing, Civil, CivilHist, Electronic, Lift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workorder);

        Electrical = findViewById(R.id.electrical);
        Hvac = findViewById(R.id.hvac);
        Plumbing = findViewById(R.id.plumbing);
        Civil = findViewById(R.id.civil);
        CivilHist = findViewById(R.id.civilHistory);
        Electronic = findViewById(R.id.electronic);
        Lift = findViewById(R.id.lift);


        Electrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Workorder.this, MyListWoElectrical.class);
                startActivity(intent);
            }
        });

        Hvac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Workorder.this, MyListWoHvac.class);
                startActivity(intent);
            }
        });

        Plumbing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Workorder.this, MyListWoPlumbing.class);
                startActivity(intent);
            }
        });

        Civil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Workorder.this, MyListWoCivil.class);
                startActivity(intent);
            }
        });

        CivilHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Workorder.this, HistoryListPmCivil.class);
                startActivity(intent);
            }
        });

        Electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Workorder.this, MyListWoElectronic.class);
                startActivity(intent);
            }
        });
        Lift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Workorder.this, MyListWoLift.class);
                startActivity(intent);
            }
        });



    }
}