package com.example.healthylives;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthylives.Database.DaysContract;
import com.example.healthylives.Database.DaysDbHelper;
import com.example.healthylives.Database.WorkoutsDbHelper;


import static java.lang.Float.parseFloat;

public class logCardioActivity extends AppCompatActivity {
    private String timeWorkout = new String();
    private String nameWorkout = new String();
    private String dateWorkout = new String();
    private String durationWorkout = new String();
    private float distanceWorkout = 0;
    private String temp=" ";
    private WorkoutsDbHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    public void onAddWork(View v)
    {
        EditText time = findViewById(R.id.timeWork);
        EditText name = findViewById(R.id.nameWork);
        EditText duration = findViewById(R.id.duration);
        EditText distance = findViewById(R.id.distance);


        timeWorkout = time.getText().toString();

        nameWorkout = name.getText().toString();
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

        time.setText("");
        name.setText("");
        duration.setText("");
        distance.setText("");


        //TODO add this data to database
        SQLiteDatabase db=mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DaysContract.DayEntry.COL_WORKOUT_TIME, timeWorkout);
        values.put(DaysContract.DayEntry.COL_WORKOUT_NAME, nameWorkout);
        values.put(DaysContract.DayEntry.COL_DAY_DATE, dateWorkout);
        values.put(DaysContract.DayEntry.COL_WORKOUT_DURATION, durationWorkout);
        values.put(DaysContract.DayEntry.COL_WORKOUT_DISTANCE, distanceWorkout);
        db.insertWithOnConflict(DaysContract.DayEntry.TABLE2, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();



    }
}
