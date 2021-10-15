package com.example.projectskripsi170101007;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class ViewDesign2 extends AppCompatActivity {

    private ImageView Container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_design2);
        Container = findViewById(R.id.imageView);

        Container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Container.setImageResource(R.drawable.ic_baseline_done_all_24_green);
            }
        });
    }

}