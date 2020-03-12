package com.example.healthylives;

import android.provider.BaseColumns;

public class DaysContract {
    public static final String DB_NAME="com.example.healthylives.db";
    public static final int DB_VERSION=1;

    public class DayEntry implements BaseColumns
    {
        public static final String TABLE="days";

        public static final String COL_DAY_DATE="date";
        public static final String COL_DAY_STEP="steps";
        public static final String COL_DAY_MIN="active_minutes";
        public static final String COL_DAY_CUP="cups";
        public static final String COL_DAY_SLEEP="sleep";

    }
}