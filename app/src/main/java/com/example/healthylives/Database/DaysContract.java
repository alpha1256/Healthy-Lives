package com.example.healthylives.Database;

import android.provider.BaseColumns;

public class DaysContract {
    public static final String DB_NAME="com.example.healthylives.db";
    public static final int DB_VERSION=1;

    public class DayEntry implements BaseColumns
    {
        public static final String TABLE1="days";
        public static final String TABLE2="workouts";
        public static final String TABLE3="heart_readings";

        public static final String COL_DAY_DATE="date";
        public static final String COL_DAY_STEP="steps";
        public static final String COL_DAY_MIN="active_minutes";
        public static final String COL_DAY_CUP="cups";
        public static final String COL_DAY_SLEEP="sleep";

        public static final String COL_WORKOUT_TIME="timestamp";
        public static final String COL_WORKOUT_NAME="name";
        public static final String COL_WORKOUT_DURATION="duration";
        public static final String COL_WORKOUT_DISTANCE="distance";

        public static final String COL_HEART_RATE="heart_rate";

    }
}