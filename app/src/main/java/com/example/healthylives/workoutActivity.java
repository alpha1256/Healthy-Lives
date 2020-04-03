package com.example.healthylives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class workoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        setTitle("Workout");
    }

    public void onClickCard(View v)
    {
        Intent intent = new Intent(this,logCardioActivity.class);
        startActivity(intent);
    }
}

//TODO add method to retrieve workout data from database
