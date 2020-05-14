package com.example.healthylives;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.healthylives.Database.DaysContract;
import com.example.healthylives.Database.DaysDbHelper;
import com.example.healthylives.Database.WorkoutsDbHelper;


import static com.example.healthylives.workoutActivity.workoutHelper;
import static java.lang.Float.parseFloat;

/**
 * This class allows logging of cardio workouts
 */
public class logCardioActivity extends AppCompatActivity {
    private String timeWorkout = new String();
    private String nameWorkout = new String();
    private String dateWorkout = new String();
    private String durationWorkout = new String();
    private float distanceWorkout = 0;
    private String temp=" ";
    //private WorkoutsDbHelper mHelper;

    /**
     * Initialize local variables, spinner and calendar
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_cardio);
        setTitle("Add New Workout");
        String [] itemSpinner = new String [] {"Swimming", "Jogging", "Cycling"};
        Spinner spinnerOne = findViewById(R.id.spinner);
        ArrayAdapter<String> spinAdapt = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, itemSpinner);
        spinnerOne.setAdapter(spinAdapt);
        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nameWorkout = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //mHelper=new WorkoutsDbHelper(this);
        CalendarView newDate = findViewById(R.id.calendarCard);
        newDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String dayDate = dayOfMonth + "-" + (month+1)  + "-" + year ;
                dateWorkout=dayDate;
            }
        });
    }

    /*@Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_log_cardio);
        setTitle("Add New Workout");
        mHelper=new WorkoutsDbHelper(this);
        CalendarView newDate = findViewById(R.id.calendarCard);
        newDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String dayDate = dayOfMonth + "-" + (month+1)  + "-" + year ;
                dateWorkout=dayDate;
            }
        });
    }*/

    /**
     * Button click which allows user add cardio workout to database
     * @param v
     */
    public void onAddWork(View v)
    {
        //EditText time = findViewById(R.id.timeWork);
        //EditText name = findViewById(R.id.nameWork);
        EditText duration = findViewById(R.id.duration);
        EditText distance = findViewById(R.id.distance);

        //Time Parsing
        TimePicker time = findViewById(R.id.timePickerWork);
        int hour = time.getCurrentHour();
        int min = 0;
        min = time.getCurrentMinute();
        String newMin = "";
        if (min < 10)
        {
            newMin = "0" + min;
        }
        else
        {
            newMin = String.valueOf(min);
        }

        String timeFormat = "";

        if (hour ==0)
        {
            hour += 12;
            timeFormat = "AM";
        }else if (hour ==12)
        {
            timeFormat = "PM";
        }else if (hour > 12)
        {
            hour -= 12;
            timeFormat = "PM";
        }else {
            timeFormat = "AM";
        }
        timeWorkout = String.valueOf(hour) + ":" + String.valueOf(newMin) + " "+ timeFormat;

        //timeWorkout = time.getText().toString();

        //nameWorkout = name.getText().toString();
        durationWorkout = duration.getText().toString();
        temp = distance.getText().toString();
        if(temp.isEmpty())
        {
            distanceWorkout=0;
        }
        else
        {
            distanceWorkout=parseFloat(temp);
        }


        //Also Add 'dateWorkout' to database

        Toast.makeText(this,"Added to DB", Toast.LENGTH_SHORT).show();

        //time.setText("");
        //name.setText("");
        duration.setText("");
        distance.setText("");

        SQLiteDatabase db=workoutHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DaysContract.DayEntry.COL_WORKOUT_TIME, timeWorkout);
        values.put(DaysContract.DayEntry.COL_WORKOUT_NAME, nameWorkout);
        values.put(DaysContract.DayEntry.COL_DAY_DATE, dateWorkout);
        values.put(DaysContract.DayEntry.COL_WORKOUT_DURATION, durationWorkout);
        values.put(DaysContract.DayEntry.COL_WORKOUT_DISTANCE, distanceWorkout);
        db.insert(DaysContract.DayEntry.TABLE2, null, values);
        db.close();
        Intent intent = new Intent(this,workoutActivity.class);
        startActivity(intent);
    }
}
