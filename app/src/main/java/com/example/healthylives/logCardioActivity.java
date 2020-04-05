package com.example.healthylives;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
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
        CalendarView newDate = findViewById(R.id.calendarCard);
        newDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String dayDate = dayOfMonth + "-" + (month+1)  + "-" + year ;
                dateWorkout=dayDate;
            }
        });
    }

    public void onAddWork(View v)
    {
        EditText name = findViewById(R.id.nameWork);
        EditText duration = findViewById(R.id.duration);
        EditText distance = findViewById(R.id.distance);


        //TODO add this data to database
        nameWorkout = name.getText().toString();
        durationWorkout = duration.getText().toString();
        distanceWorkout = distance.getText().toString();
        //Also Add 'dateWorkout' to database

        Toast.makeText(this,"Added to DB", Toast.LENGTH_SHORT).show();

        name.setText("");
        duration.setText("");
        distance.setText("");


    }
}
