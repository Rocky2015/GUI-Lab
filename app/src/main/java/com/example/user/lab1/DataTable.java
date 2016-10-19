package com.example.user.lab1;

/**
 * Created by Jason Benoit on 10/19/2016.
 */

public class DataTable {
    public DataTable() {

    }
    static final String DATABASE_NAME = "chats.db";
    static final String TABLE_NAME="chattable";
    static String COLUMN_ID = "id";
    static String COLUMN_MESSAGE = "message";


}
//    ChatDatabaseHelper chatDb = new ChatDatabaseHelper();
//chatDb.getWritableDatabase();
//        chatDb.getReadableDatabase().rawQuery("select * from chatDb where KEY_ID = ?", new String[]{DataTable.COLUMN_ID});