package com.example.healthylives.Services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.healthylives.Database.DaysContract;
import com.example.healthylives.MainActivity;

import static com.example.healthylives.MainActivity.mHelper;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class SendDataToDB extends IntentService {

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.healthylives.Services.action.FOO";
    private static final String ACTION_BAZ = "com.example.healthylives.Services.action.BAZ";

    private static final String EXTRA_PARAM1 = "com.example.healthylives.Services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.healthylives.Services.extra.PARAM2";

    public SendDataToDB() {
        super("SendDataToDB");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            intent.getAction();
            int steps = Integer.parseInt(intent.getStringExtra(MainActivity.STEPS));
            int waterCount = Integer.parseInt(intent.getStringExtra(MainActivity.WATER));
            String sleepMin = intent.getStringExtra(MainActivity.SLEEP);
            int activeMin = Integer.parseInt(intent.getStringExtra(MainActivity.ACTIVE));
            String date = intent.getStringExtra("DATE");
            //TODO add database here
            SQLiteDatabase db=mHelper.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put(DaysContract.DayEntry.COL_DAY_DATE, date);
            values.put(DaysContract.DayEntry.COL_DAY_STEP, steps);
            values.put(DaysContract.DayEntry.COL_DAY_MIN, activeMin);
            values.put(DaysContract.DayEntry.COL_DAY_CUP, waterCount);
            values.put(DaysContract.DayEntry.COL_DAY_SLEEP, sleepMin);
            db.insertWithOnConflict(DaysContract.DayEntry.TABLE1, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.close();

        }
    }

}
