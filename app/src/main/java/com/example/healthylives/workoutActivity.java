package com.example.healthylives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.healthylives.Adapter.Workout;
import com.example.healthylives.Database.DaysContract;

import java.util.ArrayList;

import static com.example.healthylives.MainActivity.mHelper;

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

    public ArrayList<Workout> getWorkouts()
    {
        ArrayList<Workout> workoutList=new ArrayList<>();
        SQLiteDatabase db=mHelper.getReadableDatabase();
        Cursor cursor=db.query(DaysContract.DayEntry.TABLE2, new String[]{DaysContract.DayEntry._ID, DaysContract.DayEntry.COL_WORKOUT_TIME, DaysContract.DayEntry.COL_WORKOUT_NAME, DaysContract.DayEntry.COL_DAY_DATE, DaysContract.DayEntry.COL_WORKOUT_DURATION, DaysContract.DayEntry.COL_WORKOUT_DISTANCE}, null, null, null, null, null);
        while (cursor.moveToNext())
        {
            int idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_WORKOUT_TIME);
            String time=cursor.getString(idx);
            idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_WORKOUT_NAME);
            String name=cursor.getString(idx);
            idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_DAY_DATE);
            String date=cursor.getString(idx);
            idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_WORKOUT_DURATION);
            String duration=cursor.getString(idx);
            idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_WORKOUT_DISTANCE);
            float distance=cursor.getFloat(idx);
            workoutList.add(new Workout(time, name, date, duration, distance));
        }
        return workoutList;
    }
}


