package com.example.user.lab1;

import android.content.Context;
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

import java.util.ArrayList;


public class ChatWindow extends AppCompatActivity {
    private final String ACTIVITY_NAME = "ChatWindow";

    static ListView listView = null;
    static Button sendButton = null;
    static ArrayList<String> messageList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In OnCreate");
        setContentView(R.layout.activity_chat_window);


        final EditText messageText = (EditText) findViewById(R.id.messageText);//initializes EditText to "message text"
        listView = (ListView) findViewById(R.id.listView);//initializes ListView "listView"
        sendButton = (Button) findViewById(R.id.sendButton);//initializes Button to the "send button"

        messageList = new ArrayList();


        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        sendButton.setOnClickListener(e -> {
            String sendChat = messageText.getText().toString();
            messageList.add(sendChat);
            messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/ getView()
            messageText.setText(" ");

        });

    }

    private class ChatAdapter extends ArrayAdapter<String>

    {
        public ChatAdapter(Context ctx) {
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
            View result = null;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            TextView message = (TextView) result.findViewById(R.id.messageText);
            message.setText(getItem(position)); // get the string at position
            return result;
        }
    }


}


