package com.example.healthylives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Visualization of all cardio workouts
 */
public class visual_workoutActivity extends AppCompatActivity {

    /**
     * Draws graph of all logged cardio workouts
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_workout);
        setTitle("Cardio Records");
        Intent intent = getIntent();
        ArrayList <Integer> distance = intent.getIntegerArrayListExtra("DISTANCE");
        ArrayList <Integer> duration = intent.getIntegerArrayListExtra("DURATION");

        LineChartView lineChartView = findViewById(R.id.chartView);

        for (int i=0; i < duration.size(); i++)
        {
            for (int j=i+1; j < duration.size(); j++)
            {
                if (duration.get(i) > duration.get(j))
                {
                    Collections.swap(duration,i,j);
                    Collections.swap(distance,i,j);
                }
            }
        }

        List yAxisValues = new ArrayList();
        List xAxisValues = new ArrayList();

        Line line = new Line(yAxisValues).setColor(Color.parseColor("#FFD300"));
        Toast.makeText(this, String.valueOf(distance.size()), Toast.LENGTH_SHORT).show();
        for (int i=0; i < distance.size(); i++)
        {
            Log.d("Distance", String.valueOf(distance.get(i)));
            Log.d("Duration", String.valueOf(duration.get(i)));
        }

        for (int i = 0; i < duration.size(); i++)
        {
            xAxisValues.add(i, new AxisValue(i).setLabel(String.valueOf(duration.get(i))));
        }

        for (int i=0; i < distance.size(); i++)
        {
            yAxisValues.add(new PointValue(i, distance.get(i)));
        }

        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        lineChartView.setLineChartData(data);

        Axis axis = new Axis();
        axis.setValues(xAxisValues);
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

        axis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setName("Distance in Miles");
        axis.setName("Duration in Minutes");

        Viewport viewport= new Viewport(lineChartView.getMaximumViewport());
        viewport.top =getMax(distance);
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }

    /**
     * Get max value of an arraylist
     * @param temp
     * @return
     */
    int getMax(ArrayList <Integer> temp)
    {
        int max=0;
        for (int i=0; i < temp.size(); i++)
        {
            if (temp.get(i) > max)
            {
                max = temp.get(i);
            }
        }
        return max;
    }
}
