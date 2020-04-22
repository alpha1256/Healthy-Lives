package com.example.healthylives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class visual_workoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_workout);
        setTitle("Cardio Records");
        Intent intent = getIntent();
        ArrayList <Integer> distance = intent.getIntegerArrayListExtra("DISTANCE");
        ArrayList <Integer> duration = intent.getIntegerArrayListExtra("DURATION");


         final GraphView graph = findViewById(R.id.graphVisual);
         graph.setVisibility(View.VISIBLE);

         DataPoint[] dataPoints = new DataPoint[duration.size()];
         for (int i =0; i < duration.size(); i++)
         {
         dataPoints[i] = new DataPoint(distance.get(i), duration.get(i));
         }
         LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
         graph.addSeries(series);
    }
}
