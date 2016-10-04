package com.example.user.lab1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {
    public final String ACTIVITY_NAME = "StartActivity";

    protected Button button;
    Context context ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In OnCreate");
        setContentView(R.layout.activity_start);
        context=getApplicationContext();

        button = (Button)findViewById(R.id.imaButton);
        button.setOnClickListener(e ->startActivityForResult(new Intent( context, ListItemsActivity.class ), 5));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In OnResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy");
    }

    @Override
    public void onActivityResult(int requestCode, int responceCode, Intent data){
        if(requestCode == 5)//coming back from ListITemsActivity
        {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
            Toast.makeText(context, "Im back!!!", Toast.LENGTH_LONG);
        }
        if(responceCode == RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            Toast.makeText(StartActivity.this,messagePassed,Toast.LENGTH_LONG).show();

        }
    }
}
