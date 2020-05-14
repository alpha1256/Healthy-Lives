package com.example.healthylives.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healthylives.Adapter.Workout;
import com.example.healthylives.Database.DaysContract;
import com.example.healthylives.Database.WorkoutsDbHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} workout service that pushes a new workout to firebase
 * a service on a separate handler thread.
 * helper methods.
 */
public class WorkoutService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.healthylives.Services.action.FOO";
    private static final String ACTION_BAZ = "com.example.healthylives.Services.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.healthylives.Services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.healthylives.Services.extra.PARAM2";

    public WorkoutService() {
        super("WorkoutService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent!=null)
        {
            WorkoutsDbHelper mWorkoutHelper=new WorkoutsDbHelper(this);
            List<Workout> workoutList=new ArrayList<>();
            SQLiteDatabase db=mWorkoutHelper.getReadableDatabase();
            Cursor cursor=db.query(DaysContract.DayEntry.TABLE2, new String[]{DaysContract.DayEntry.COL_WORKOUT_TIME, DaysContract.DayEntry.COL_WORKOUT_NAME, DaysContract.DayEntry.COL_DAY_DATE, DaysContract.DayEntry.COL_WORKOUT_DURATION, DaysContract.DayEntry.COL_WORKOUT_DISTANCE}, null, null, null, null, null);
            if(cursor.getCount()<1)
            {
                return;
            }
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
                Workout work = new Workout(time,name,date,duration,distance);
                work.setEmail(intent.getStringExtra("email"));
                //workoutList.add(new Workout(time, name, date, duration, distance));
                workoutList.add(work);
            }
            FirebaseDatabase mDatabase;
            DatabaseReference mDatabaseReference;
            mDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference=mDatabase.getReference();
            mDatabaseReference=mDatabase.getReference().child("workouts");
            mDatabaseReference.setValue(workoutList);
        }
    }


}
