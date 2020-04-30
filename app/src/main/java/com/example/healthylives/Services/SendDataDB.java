package com.example.healthylives.Services;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.healthylives.Database.DaysContract;
import com.example.healthylives.MainActivity;

import static com.example.healthylives.MainActivity.mHelper;

public class SendDataDB extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent != null)
        {
            intent.getAction();
            Log.d("AlarmDebug", "Success");
            int steps = Integer.parseInt(intent.getStringExtra(MainActivity.STEPS));
            //int waterCount = Integer.parseInt(intent.getStringExtra(MainActivity.WATER));
            int waterCount =0;
            String sleepMin = intent.getStringExtra(MainActivity.SLEEP);
            int activeMin = Integer.parseInt(intent.getStringExtra(MainActivity.ACTIVE));
            String date = intent.getStringExtra("DATE");
            
            SQLiteDatabase db = mHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DaysContract.DayEntry.COL_DAY_DATE, date);
            values.put(DaysContract.DayEntry.COL_DAY_STEP, steps);
            values.put(DaysContract.DayEntry.COL_DAY_MIN, activeMin);
            values.put(DaysContract.DayEntry.COL_DAY_CUP, waterCount);
            values.put(DaysContract.DayEntry.COL_DAY_SLEEP, sleepMin);
            db.insertWithOnConflict(DaysContract.DayEntry.TABLE1, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.close();
        }
        else {
            Log.d("AlarmDebug", "NULL");
        }
    }


}
