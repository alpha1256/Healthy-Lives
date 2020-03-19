package com.example.healthylives;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class sleepActivity extends AppCompatActivity {

    TextView recordSleep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        setTitle("Sleep");
        recordSleep = (TextView) findViewById(R.id.recordSleep);
    }

    public void onClickStart(View v)
    {
        recordSleep.setText(String.valueOf("recording"));
    }

    public void onClickStop (View v)
    {
        recordSleep.setText(" ");
    }
}
