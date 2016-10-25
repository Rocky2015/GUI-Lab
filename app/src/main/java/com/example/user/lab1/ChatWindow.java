package com.example.user.lab1;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
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

import java.util.ArrayList;


public class ChatWindow extends AppCompatActivity {
    Button sendButton;
    ListView listView;
    EditText messageText;
    ChatAdapter messageAdapter;

    Cursor cursor;
    Context ctx = this;
    ChatDatabaseHelper sampleDB = new ChatDatabaseHelper(ctx);
    ArrayList<String> messageList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor newEditor;
    boolean aBoolean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String ACTIVITY_NAME = "ChatWindow";
        Log.i(ACTIVITY_NAME, "In OnCreate");
        setContentView(R.layout.activity_chat_window);
        cursor = sampleDB.getInfo(sampleDB);

        messageText = (EditText) findViewById(R.id.messageText);
        listView = (ListView) findViewById(R.id.listView);
        sendButton = (Button) findViewById(R.id.sendButton);

        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);
        sharedPreferences = getSharedPreferences("exists", Context.MODE_PRIVATE);
        aBoolean = sharedPreferences.getBoolean("exists", false);
        cursor.moveToFirst();

        if (aBoolean) {
            do {
                messageList.add(cursor.getString(0).toString());
                Log.i("ChatWindow", "Cursor's column count = " + cursor.getColumnName(cursor.getColumnIndex(DataTable.COLUMN_MESSAGE)));
                Log.i("ChatWindow", "Cursor's column count = " + cursor.getColumnCount());
                Log.i("ChatWindow", "SQL MESSAGE" + cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_MESSAGE)));
            } while (cursor.moveToNext());
        }

        sendButton.setOnClickListener((v) -> {
            messageList.add(messageText.getText().toString());
            sampleDB.onInsert(sampleDB, messageText.getText().toString());

            newEditor = sharedPreferences.edit();
            newEditor.putBoolean("exists", true);
            newEditor.apply();

            messageAdapter.notifyDataSetChanged();
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


