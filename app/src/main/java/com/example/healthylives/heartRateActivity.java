package com.example.healthylives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class heartRateActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensormanager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);
        setTitle("Heart Rate");
        mSensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensormanager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        mSensormanager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        //TODO
    }


    @Override
    public final void onSensorChanged(SensorEvent event)
    {

    }
}
