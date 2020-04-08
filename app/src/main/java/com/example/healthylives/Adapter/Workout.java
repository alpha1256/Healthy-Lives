package com.example.healthylives.Adapter;

public class Workout {
    private String time;
    private String name;
    private String date;
    private String duration;
    private float distance;

    public Workout(String t, String n, String da, String du, float di)
    {
        time=t;
        name=n;
        date=da;
        duration=du;
        distance=di;
    }

    public String getTime()
    {
        return time;
    }

    public String getName()
    {
        return name;
    }

    public String getDate()
    {
        return date;
    }

    public String getDuration()
    {
        return duration;
    }

    public float getDistance()
    {
        return distance;
    }
}
