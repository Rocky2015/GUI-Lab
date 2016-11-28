package com.example.user.lab1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public final class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static String KEY_ID = "Id";
    public static String KEY_MESSAGE = "Message";

    public static final String DATABASE_NAME = "chats.db";
    public static final String TABLE_NAME = "ChatTable";
    public static final int VERSION_NUM = 2;

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
        Log.d("Database Operations", "Database Created");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "
                + TABLE_NAME
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_MESSAGE + " TEXT NOT NULL);"
        );
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + "newVersion=" + newVersion);
        onCreate(db);
    }
}
