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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


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
                String dayDate = dayOfMonth + "-" + month  + "-" + year ;
                date=dayDate;
            }
        });
    }


    public void addNewWorkoutPlan(View v)
    {
        TimePicker newTime = findViewById(R.id.timePicker);
        EditText text = findViewById(R.id.noteWorkout);

        String note = String.valueOf(text.getText());
        int hour = newTime.getCurrentHour();
        int min = newTime.getCurrentMinute();
        String time = String.valueOf(hour) + ":" + String.valueOf(min);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String currentDateandTime = sdf.format(new Date());

        Toast.makeText(this,currentDateandTime,Toast.LENGTH_SHORT).show();
        String data = date + " / " + time + " /" + note+"\n";
        text.setText("");
        try{
            FileOutputStream stream = new FileOutputStream(new File(getFilesDir(),"all_workout.txt"), true);
            stream.write(data.getBytes());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void onClickView(View v)
    {
        Intent intent = new Intent(this, viewPlanActivity.class);
        startActivity(intent);
    }



}
