package com.example.healthylives;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private int waterCount=0;
    private int steps =0;
    private int counterSteps =0;
    private String date;
    private String activeMin="00:00";
    private String sleepMin="00:00";
    private SensorManager mSensormanager;
    private Sensor mSensor;
    private DaysDbHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensormanager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensormanager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_NORMAL);
        mHelper=new DaysDbHelper(this);
        getDate();
        onTwentyFour();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.hr)
        {
            Intent intent = new Intent(this, heartRateActivity.class);
            startActivity(intent);
            return false;
        }

        else if (id == R.id.workout)
        {
            Intent intent = new Intent(this, workoutActivity.class);
            startActivity(intent);
            return false;
        }

        else if (id == R.id.sleep)
        {
            Intent intent = new Intent(this, sleepActivity.class);
            startActivity(intent);
            return false;
        }

        else if (id == R.id.social)
        {
            Intent intent = new Intent(this, socialActivity.class);
            startActivity(intent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickAdd(View v)
    {
        TextView water = (TextView) findViewById(R.id.waterCounter);
        waterCount = waterCount +1;
        water.setText(String.valueOf(waterCount));
    }

    public void onClickSubtract(View v)
    {
        TextView water = (TextView) findViewById(R.id.waterCounter);
        waterCount= waterCount -1;
        if (waterCount <= 0)
        {
            water.setText(String.valueOf(0));
            waterCount =0;
        }
        else
            water.setText(String.valueOf(waterCount));
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        //TODO
    }

    /**
     * This is the step counting algorithm
     * @param event
     */
    @Override
    public final void onSensorChanged(SensorEvent event)
    {
        //float step = event.values[0];
        if (counterSteps < 1)
            counterSteps = (int) event.values[0];
        steps = (int) event.values[0] - counterSteps;
        TextView stepCount = (TextView) findViewById(R.id.stepCounter);
        stepCount.setText(String.valueOf(steps));
    }

    /**
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //Steps then step counter
        String message = steps + "\n" + counterSteps;
        try{
            FileOutputStream saveFile = new FileOutputStream(new File(getFilesDir(),"step.txt"));
            saveFile.write(message.getBytes());
            Toast.makeText(this,"message", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        try{
            InputStream fis = new FileInputStream(new File(getFilesDir(), "step.txt"));
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            if (br.readLine() == null)
            {
                Toast.makeText(this, "On destroy wasnt called", Toast.LENGTH_SHORT).show();
            }
            else
            {
                int counter =0;
                while (br.readLine() != null)
                {
                    if (counter ==0)
                    {
                        steps = Integer.parseInt(br.readLine());
                        counter++;
                    }
                    else
                        counterSteps = Integer.parseInt(br.readLine());
                }
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }**/


    /**
    @Override
    public void onStop()
    {
        super.onStop();
        mSensormanager.unregisterListener(this);
    }**/

    /**
     * Every twenty four hours update db and reset step and water
     */
    public void onTwentyFour()
    {
        //TODO add to database before clearing local variables
        SQLiteDatabase db=mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DaysContract.DayEntry.COL_DAY_DATE, date);
        values.put(DaysContract.DayEntry.COL_DAY_STEP, steps);
        values.put(DaysContract.DayEntry.COL_DAY_MIN, activeMin);
        values.put(DaysContract.DayEntry.COL_DAY_CUP, waterCount);
        values.put(DaysContract.DayEntry.COL_DAY_SLEEP, sleepMin);
        db.insertWithOnConflict(DaysContract.DayEntry.TABLE1, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        Timer timer =  new Timer();
        TimerTask t = new TimerTask (){
            @Override
            public void run()
            {
                getDate();
                waterCount =0;
                steps =0;
                counterSteps =0;
                activeMin="00:00";
                sleepMin="00:00";
            }
        };
    }

    public void getDate()
    {
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yy");
        date=sdf.format(new Date());
    }

    public void getSleep()
    {
        //TODO get sleep data from sleep activity
    }
}
