package com.example.user.lab1;

import android.provider.BaseColumns;

/**
 * Created by Jason Benoit on 10/19/2016.
 */

public class DataTable implements BaseColumns {
    public DataTable() {

    }

    static final String DATABASE_NAME = "chats.db";
    static final String TABLE_NAME = "chattable";
    static String COLUMN_ID = "id";
    static String COLUMN_MESSAGE = "message";


}
