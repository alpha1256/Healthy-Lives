package com.example.healthylives;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class logCardioActivity extends AppCompatActivity {
    private String nameWorkout = new String();
    private String dateWorkout = new String();
    private String durationWorkout = new String();
    private String distanceWorkout = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_cardio);
        setTitle("Add New Workout");
    }

    public void onAddWork(View v)
    {
        EditText name = findViewById(R.id.nameWork);
        EditText date = findViewById(R.id.dateWork);
        EditText duration = findViewById(R.id.duration);
        EditText distance = findViewById(R.id.distance);

        nameWorkout = name.getText().toString();
        dateWorkout = date.getText().toString();
        durationWorkout = duration.getText().toString();
        distanceWorkout = distance.getText().toString();

        Toast.makeText(this,"Added to DB", Toast.LENGTH_SHORT).show();

        name.setText("");
        date.setText("");
        duration.setText("");
        distance.setText("");

        //TODO add this data to database
    }
}
