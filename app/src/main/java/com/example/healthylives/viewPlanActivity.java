package com.example.healthylives;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class viewPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plan);
        setTitle("All Workout Plans");
    }
}
