package com.example.user.lab1;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    Cursor cursor;
    Button sendButton;
    ListView listView;
    EditText messageText;
    ArrayList<String> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String ACTIVITY_NAME = "ChatWindow";
        Log.i(ACTIVITY_NAME, "In OnCreate");
        setContentView(R.layout.activity_chat_window);

        messageText = (EditText) findViewById(R.id.messageText);
        listView = (ListView) findViewById(R.id.listView);
        sendButton = (Button) findViewById(R.id.sendButton);

        String query = "SELECT " + ChatDatabaseHelper.KEY_MESSAGE + " FROM " + ChatDatabaseHelper.TABLE_NAME;
        ChatDatabaseHelper tempDB = new ChatDatabaseHelper(this);
        SQLiteDatabase db = tempDB.getWritableDatabase();
        cursor = db.rawQuery(query, null);
        messageList.add(cursor.getColumnName(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

        sendButton.setOnClickListener(e -> {
            messageList.add(messageText.getText().toString());
            ContentValues values = new ContentValues();
            values.put(ChatDatabaseHelper.KEY_MESSAGE, messageText.getText().toString());
            db.update(ChatDatabaseHelper.TABLE_NAME, values, ChatDatabaseHelper.KEY_MESSAGE + "=?", new String[]{String.valueOf(ChatDatabaseHelper.KEY_MESSAGE)});
            Toast.makeText(getBaseContext(), "insert sucessful", Toast.LENGTH_LONG).show();
        });

        while (!cursor.isAfterLast()) {
            messageList.add(cursor.getString(0).toString());
            Log.i(ACTIVITY_NAME, "SQL MESSAGE" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
        }

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            Log.i("ChatWindow ", " Cursor’s  column count = " + cursor.getColumnName(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
        }
        db.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class ChatAdapter extends ArrayAdapter<String>

    {
        ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return messageList.size();
        }

        public String getItem(int position) {
            return messageList.get(position);
        }

        @NonNull
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;
        }
    }
}


