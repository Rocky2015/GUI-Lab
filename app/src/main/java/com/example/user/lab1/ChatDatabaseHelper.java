package com.example.user.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static final int VERSION_NUM = 1;
    public static String DATABASE_CREATE = "CREATE TABLE "
            + DataTable.TABLE_NAME
            + "("
            + DataTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DataTable.COLUMN_MESSAGE + " TEXT);";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DataTable.DATABASE_NAME, null, VERSION_NUM);
        Log.d("Database Operations", "Database Created");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        Log.d("Database Operations", "Database Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataTable.DATABASE_NAME);
        Log.d("Database Operations", "Database Dropped");
        onCreate(db);
    }

    public void onInsert(ChatDatabaseHelper cdhelper, String message) {
        SQLiteDatabase writableDb = cdhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataTable.COLUMN_MESSAGE, message);
        writableDb.insert(DataTable.TABLE_NAME, null, contentValues);
    }

    public Cursor getInfo(ChatDatabaseHelper chatDatabaseHelper) {
        SQLiteDatabase SQuery = chatDatabaseHelper.getReadableDatabase();
        String[] message = {DataTable.COLUMN_MESSAGE};
        Cursor cursor = SQuery.query(DataTable.TABLE_NAME, message, null, null, null, null, null);
        return cursor;

    }
}
