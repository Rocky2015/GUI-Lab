package com.example.user.lab1;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    SQLiteDatabase db;
    Cursor cursor;
    Button sendButton;
    ListView listView;
    EditText messageText;
    ArrayList<String> messageList = new ArrayList<>();
    ContentValues values = new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String ACTIVITY_NAME = "ChatWindow";
        Log.i(ACTIVITY_NAME, "In OnCreate");
        setContentView(R.layout.activity_chat_window);

        messageText = (EditText) findViewById(R.id.messageText);
        listView = (ListView) findViewById(R.id.listView);
        sendButton = (Button) findViewById(R.id.sendButton);

        ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        String query = "SELECT " + ChatDatabaseHelper.KEY_MESSAGE + " FROM " + ChatDatabaseHelper.TABLE_NAME;
        ChatDatabaseHelper tempDB = new ChatDatabaseHelper(this);
        db = tempDB.getWritableDatabase();
        cursor = db.rawQuery(query, null);
        messageList.add(cursor.getColumnName(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            messageList.add(cursor.getString(0).toString());
            Log.i(ACTIVITY_NAME, "SQL MESSAGE" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
            cursor.moveToNext();

        }

        for (int i = 0; i < cursor.getCount(); i++) {

            Log.i("ChatWindow ", " Cursor’s  column count = " + cursor.getColumnName(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
        }
        sendButton.setOnClickListener(e -> {
            messageList.add(messageText.getText().toString());
            messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/ getView()

            values.put(ChatDatabaseHelper.KEY_MESSAGE, messageText.getText().toString());
            db.insert(ChatDatabaseHelper.TABLE_NAME, null, values);
            cursor.moveToNext();
            Toast.makeText(getBaseContext(), "insert successful", Toast.LENGTH_LONG).show();
            messageText.setText("");
        });

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


        public View getView(int position, View convertView, ViewGroup parent) {
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

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}


