package com.example.healthylives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.healthylives.Adapter.Workout;
import com.example.healthylives.Database.DaysContract;
import com.example.healthylives.Database.DaysDbHelper;
import com.example.healthylives.Database.WorkoutsDbHelper;


import java.util.ArrayList;
import java.util.List;

/**
 * Workout activity which takes user to logcardio or visual_workout. Also sends all cardio workout from db to visual_workout activity
 */
public class workoutActivity extends AppCompatActivity {

    public static WorkoutsDbHelper workoutHelper;
    private ArrayList <Integer> durationList = new ArrayList<Integer>();
    private ArrayList <Integer> distanceList = new ArrayList<Integer>();

    /**
     * Sends distance and duration list to visual_workout Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        workoutHelper=new WorkoutsDbHelper(this);
        setTitle("Workout");
        //Uncomment this block to test getWorkout
        if (getWorkouts() != null) {
            ArrayList<Workout> temp = getWorkouts();
            for (int i = 0; i < temp.size(); i++) {
                //Log.d("TList date", String.valueOf(temp.get(i).getDate()));
                //Log.d("Tlist duration", String.valueOf(temp.get(i).getDuration()));
                durationList.add(Integer.parseInt(temp.get(i).getDuration()));
                distanceList.add(Math.round(temp.get(i).getDistance()));
            }
        }
    }

    /**
     * Button click which sends user to logcardio Activity
     * @param v
     */
    public void onClickCard(View v)
    {
        Intent intent = new Intent(this,logCardioActivity.class);
        startActivity(intent);
    }

    /**
     * Button click which takes user to visual_workout activity and also sends the durationlist and distancelist to this activyt
     * @param v
     */
    public void onClickVisual(View v)
    {
        Intent intent = new Intent(this, visual_workoutActivity.class);
        intent.putExtra("DURATION", durationList);
        intent.putExtra("DISTANCE", distanceList);
        startActivity(intent);
    }

    /**
     * Get cardio workouts from sqlite database and returns this in a workout list
     * @return
     */
    public ArrayList<Workout> getWorkouts()
    {
        ArrayList<Workout> workoutList=new ArrayList<>();
        SQLiteDatabase db=workoutHelper.getReadableDatabase();
        Cursor cursor=db.query(DaysContract.DayEntry.TABLE2, new String[]{DaysContract.DayEntry.COL_WORKOUT_TIME, DaysContract.DayEntry.COL_WORKOUT_NAME, DaysContract.DayEntry.COL_DAY_DATE, DaysContract.DayEntry.COL_WORKOUT_DURATION, DaysContract.DayEntry.COL_WORKOUT_DISTANCE}, null, null, null, null, null);
        if(cursor.getCount()<1)
        {
            return workoutList;
        }
        if(cursor.moveToFirst())
        {
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
        }

        return workoutList;
    }


}


