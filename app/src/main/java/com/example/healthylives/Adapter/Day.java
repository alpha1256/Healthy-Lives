package com.example.healthylives.Adapter;

public class Day {
    String date;
    int steps;
    String min;
    int cups;
    String sleep;

    public Day(String d, int st, String m, int c, String sl)
    {
        date=d;
        steps=st;
        min=m;
        cups=c;
        sleep=sl;
    }

    public String getDate() {
        return date;
    }

    public int getSteps() {
        return steps;
    }

    public String getMin() {
        return min;
    }

    public int getCups() {
        return cups;
    }

    public String getSleep() {
        return sleep;
    }
}
