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
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;


public class workoutActivity extends AppCompatActivity {

    public static WorkoutsDbHelper workoutHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        workoutHelper=new WorkoutsDbHelper(this);
        setTitle("Workout");
        //Uncomment this block to test getWorkout
        ArrayList<Workout> temp = getWorkouts();
        ArrayList <Integer> duration = new ArrayList<Integer>();
        ArrayList <Integer> distance = new ArrayList<Integer>();
        for (int i=0; i < temp.size(); i++)
        {
            Log.d("TList date", String.valueOf(temp.get(i).getDate()));
            Log.d("Tlist duration", String.valueOf(temp.get(i).getDuration()));
            duration.add(Integer.parseInt(temp.get(i).getDuration()));
            distance.add(Math.round(temp.get(i).getDistance()));
        }

        final GraphView graph = findViewById(R.id.graphVisual);
        graph.setVisibility(View.VISIBLE);

        DataPoint [] dataPoints = new DataPoint[duration.size()];
        for (int i =0; i < duration.size(); i++)
        {
            dataPoints[i] = new DataPoint(duration.get(i), distance.get(i));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
        graph.addSeries(series);
    }

    public void onClickCard(View v)
    {
        Intent intent = new Intent(this,logCardioActivity.class);
        startActivity(intent);
    }

    public ArrayList<Workout> getWorkouts()
    {
        ArrayList<Workout> workoutList=new ArrayList<>();
        SQLiteDatabase db=workoutHelper.getReadableDatabase();
        Cursor cursor=db.query(DaysContract.DayEntry.TABLE2, new String[]{DaysContract.DayEntry.COL_WORKOUT_TIME, DaysContract.DayEntry.COL_WORKOUT_NAME, DaysContract.DayEntry.COL_DAY_DATE, DaysContract.DayEntry.COL_WORKOUT_DURATION, DaysContract.DayEntry.COL_WORKOUT_DISTANCE}, null, null, null, null, null);
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


