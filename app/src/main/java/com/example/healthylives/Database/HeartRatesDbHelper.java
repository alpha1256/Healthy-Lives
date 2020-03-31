package com.example.healthylives.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.healthylives.Database.DaysContract;

public class HeartRatesDbHelper extends SQLiteOpenHelper {
    public HeartRatesDbHelper(Context context)
    {
        super(context, DaysContract.DB_NAME, null, DaysContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTable="CREATE TABLE "+DaysContract.DayEntry.TABLE3 + "(" + DaysContract.DayEntry.COL_WORKOUT_TIME + " TEXT NOT NULL, " + DaysContract.DayEntry.COL_HEART_RATE + " INTEGER);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DaysContract.DayEntry.TABLE3);
        onCreate(db);
    }
}
