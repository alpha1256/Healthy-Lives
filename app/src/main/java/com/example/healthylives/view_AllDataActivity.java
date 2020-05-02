package com.example.healthylives;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.example.healthylives.Adapter.Day;
import com.example.healthylives.Adapter.mainDataAdapter;
import com.example.healthylives.Database.DaysContract;
import com.example.healthylives.Database.DaysDbHelper;

import java.util.ArrayList;

public class view_AllDataActivity extends AppCompatActivity {
    public static DaysDbHelper mHelper;
    private RecyclerView recycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private mainDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__all_data);
        setTitle("Your Data");
        mHelper=new DaysDbHelper(this);

        if(getData() != null)
        {
            ArrayList <Day> temp = getData();
            Toast.makeText(this, String.valueOf(temp.size()), Toast.LENGTH_SHORT).show();
            recycler = findViewById(R.id.all_Data);
            recycler.setHasFixedSize(true);
            recycler.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));
            adapter = new mainDataAdapter(temp);
            layoutManager = new LinearLayoutManager(this);
            recycler.setLayoutManager(layoutManager);
            recycler.setItemAnimator(new DefaultItemAnimator());
            recycler.setAdapter(adapter);

        }
    }

    //TODO add method to retrieve this data from the database
    public ArrayList<Day> getData()
    {
        ArrayList<Day> dayList=new ArrayList<>();
        SQLiteDatabase db=mHelper.getReadableDatabase();
        Cursor cursor=db.query(DaysContract.DayEntry.TABLE1, new String[]{DaysContract.DayEntry.COL_DAY_DATE, DaysContract.DayEntry.COL_DAY_STEP, DaysContract.DayEntry.COL_DAY_MIN, DaysContract.DayEntry.COL_DAY_CUP, DaysContract.DayEntry.COL_DAY_SLEEP}, null, null, null, null, null);
        if(cursor.moveToFirst())
        {
            while (cursor.moveToNext())
            {
                int idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_DAY_DATE);
                String tempDate=cursor.getString(idx);
                idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_DAY_STEP);
                int tempStep=cursor.getInt(idx);
                idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_DAY_MIN);
                String tempMin=cursor.getString(idx);
                idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_DAY_CUP);
                int tempCup=cursor.getInt(idx);
                idx=cursor.getColumnIndex(DaysContract.DayEntry.COL_DAY_SLEEP);
                String tempSleep=cursor.getString(idx);
                dayList.add(new Day(tempDate, tempStep, tempMin, tempCup, tempSleep));
            }
        }
        return dayList;
    }
}
