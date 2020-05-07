package com.example.healthylives.Adapter;

public class Workout {
    private String time;
    private String name;
    private String date;
    private String duration;
    private String email;
    private float distance;

    public Workout()
    {
        //required for Firebase usage
    }

    public Workout(String t, String n, String da, String du, float di)
    {
        time=t;
        name=n;
        date=da;
        duration=du;
        distance=di;
        email = new String ();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime()
    {
        return time;
    }

    public String getEmail(){return email;}

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
