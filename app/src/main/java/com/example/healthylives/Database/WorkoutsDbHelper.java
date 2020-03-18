package com.example.healthylives.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.healthylives.Database.DaysContract;

public class WorkoutsDbHelper extends SQLiteOpenHelper {
    public WorkoutsDbHelper(Context context)
    {
        super(context, DaysContract.DB_NAME, null, DaysContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTable="CREATE TABLE "+DaysContract.DayEntry.TABLE2 + "(" + DaysContract.DayEntry.COL_WORKOUT_TIME + " TEXT NOT NULL PRIMARY KEY, " + DaysContract.DayEntry.COL_WORKOUT_NAME + " TEXT NOT NULL, " + DaysContract.DayEntry.COL_DAY_DATE + " TEXT NOT NULL, " + DaysContract.DayEntry.COL_WORKOUT_DURATION + " TEXT NOT NULL, " + DaysContract.DayEntry.COL_WORKOUT_DISTANCE + " FLOAT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DaysContract.DayEntry.TABLE2);
        onCreate(db);
    }
}
