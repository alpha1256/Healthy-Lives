package com.example.healthylives.Adapter;

public class workoutPlan {
    private String date;
    private String time;
    private String note;

    public workoutPlan()
    {
        date = null;
        time = null;
        note = null;
    }

    public workoutPlan(String d, String t, String n)
    {
        date = d;
        time = t;
        note = n;
    }

    public void setDate (String d)
    {
        date = d;
    }

    public void setTime(String t)
    {
        time = t;
    }

    public void setNote(String n)
    {
        note = n;
    }

    public String getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    public String getTime() {
        return time;
    }
}
