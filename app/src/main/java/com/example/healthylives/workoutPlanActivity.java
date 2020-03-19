package com.example.healthylives;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class workoutPlanActivity extends AppCompatActivity {
    String date = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_plan);
        setTitle("Schedule New Workout");
        CalendarView newDate = findViewById(R.id.calendarWork);
        newDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String dayDate = dayOfMonth + "-" + (month + 1) + "-" + year ;
                date=dayDate;
            }
        });
    //TODO add adapter
    }

    //TODO add notification alerting them to go to gym when it correct time

    public void addNewWorkoutPlan(View v)
    {
        TimePicker newTime = findViewById(R.id.timePicker);
        EditText text = findViewById(R.id.noteWorkout);

        String note = String.valueOf(text.getText());
        int hour = newTime.getCurrentHour();
        int min = newTime.getCurrentMinute();
        String time = String.valueOf(hour) + ":" + String.valueOf(min);

        Toast.makeText(this,time,Toast.LENGTH_SHORT).show();
    }

    public void onClickView(View v)
    {
        Intent intent = new Intent(this, viewPlanActivity.class);
        startActivity(intent);
    }



}
