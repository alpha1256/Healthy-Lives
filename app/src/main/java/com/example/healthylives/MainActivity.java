package com.example.healthylives;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private int waterCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
