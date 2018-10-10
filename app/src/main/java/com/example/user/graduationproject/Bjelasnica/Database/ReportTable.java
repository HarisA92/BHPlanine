package com.example.user.graduationproject.Bjelasnica.Database;

import android.provider.BaseColumns;

public class ReportTable {

    public ReportTable(){}

    public static final class UserTable implements BaseColumns{
        public static final String TABLE_NAME = "report_table";
        public static final String USERNAME = "USERNAME";
        public static final String COMMENTBOX = "COMMENTBOX";
        public static final String IMAGE = "IMAGE";
        public static final String SNOW = "SNOW";
        public static final String SURFACE = "SURFACE";
        public static final String COLUMN_TIMESTAMP = "TIMESTAMP";
    }
}
