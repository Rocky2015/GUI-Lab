package com.example.user.lab1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    private final String ACTIVITY_NAME = "ChatWindow";
    protected ListView listView;
    protected EditText messageText;
    protected Button sendButton;
    protected ArrayList<String> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In OnCreate");
        setContentView(R.layout.activity_chat_window);

        listView = (ListView) findViewById(R.id.listView);//initializes ListView "listView"
        messageText = (EditText) findViewById(R.id.messageText);//initializes EditText to "message text"
        sendButton = (Button) findViewById(R.id.sendButton);//initializes Button to the "send button"
        messageList = new ArrayList();

        sendButton.setOnClickListener(e -> {
            messageList.add(messageText.getText().toString());
        });

    }

    private ChatAdapter extends BaseAdapter<String>

    {
        public ChatAdapter(Context ctx) {
        super(ctx, 0);
    }

    public int getCount() {
        return messageList.size();
    }
}


