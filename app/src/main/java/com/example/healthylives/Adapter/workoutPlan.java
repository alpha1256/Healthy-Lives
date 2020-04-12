package com.example.healthylives.Adapter;

import java.io.Serializable;

public class workoutPlan implements Serializable {
    private String date;
    private String time;
    private String note;
    private boolean checkMark;

    public workoutPlan()
    {
        date = null;
        time = null;
        note = null;
        checkMark = false;
    }

    public workoutPlan(String d, String t, String n)
    {
        date = d;
        time = t;
        note = n;
        checkMark = false;
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

    public void setCheckMark(boolean c){checkMark = c;}

    public String getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    public String getTime() {
        return time;
    }

    public boolean getCheckMark(){return checkMark;}

}
