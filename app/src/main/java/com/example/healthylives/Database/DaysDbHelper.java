package com.example.healthylives.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.healthylives.Database.DaysContract;

public class DaysDbHelper extends SQLiteOpenHelper {
    public DaysDbHelper(Context context)
    {
        super(context, DaysContract.DB_NAME, null, DaysContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTable="CREATE TABLE "+DaysContract.DayEntry.TABLE1 + "(" + DaysContract.DayEntry.COL_DAY_DATE + " TEXT NOT NULL, " + DaysContract.DayEntry.COL_DAY_STEP + " INTEGER, " + DaysContract.DayEntry.COL_DAY_MIN + " TEXT NOT NULL, " + DaysContract.DayEntry.COL_DAY_CUP + " INTEGER, " + DaysContract.DayEntry.COL_DAY_SLEEP + " TEXT NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DaysContract.DayEntry.TABLE1);
        onCreate(db);
    }
}
