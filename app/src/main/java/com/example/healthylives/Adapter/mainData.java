package com.example.healthylives.Adapter;

public class mainData {
    int steps;
    int waterCount;
    int activeMin;
    String sleep;
    String date;

    public mainData(int s, int w, int a, String sle, String d)
    {
        steps = s;
        waterCount = w;
        activeMin = a;
        sleep = sle;
        date = d;
    }

    public void setActiveMin(int activeMin) {
        this.activeMin = activeMin;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setWaterCount(int waterCount) {
        this.waterCount = waterCount;
    }

    public String getDate() {
        return date;
    }

    public int getActiveMin() {
        return activeMin;
    }

    public int getSteps() {
        return steps;
    }

    public int getWaterCount() {
        return waterCount;
    }

    public String getSleep() {
        return sleep;
    }
}
