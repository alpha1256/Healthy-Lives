package com.example.healthylives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthylives.Database.DaysContract;

import static com.example.healthylives.MainActivity.mHelper;

public class logCardioActivity extends AppCompatActivity {
    private String timeWorkout = new String();
    private String nameWorkout = new String();
    private String dateWorkout = new String();
    private String durationWorkout = new String();
    private String distanceWorkout = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_cardio);
        setTitle("Add New Workout");
    }

    public void onAddWork(View v)
    {
        EditText time = findViewById(R.id.timeWork);
        EditText name = findViewById(R.id.nameWork);
        EditText date = findViewById(R.id.dateWork);
        EditText duration = findViewById(R.id.duration);
        EditText distance = findViewById(R.id.distance);

        timeWorkout = time.getText().toString();
        nameWorkout = name.getText().toString();
        dateWorkout = date.getText().toString();
        durationWorkout = duration.getText().toString();
        distanceWorkout = distance.getText().toString();

        Toast.makeText(this,"Added to DB", Toast.LENGTH_SHORT).show();

        time.setText("");
        name.setText("");
        date.setText("");
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
