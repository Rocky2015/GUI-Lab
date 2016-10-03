package com.example.user.lab1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    public static SharedPreferences sharedPref;
    public static SharedPreferences.Editor editor;
    protected Button button;
    protected String myEmail;
    final Context context = getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In OnCreate");
        setContentView(R.layout.activity_login);

        final EditText emailAddress = (EditText)findViewById(R.id.loginEmail);

        sharedPref = context.getSharedPreferences("prefsfile", MODE_PRIVATE);
        emailAddress.setText( sharedPref.getString("email", "email@domain.com") );

        button = (Button)findViewById(R.id.loginButton1);

        button.setOnClickListener(e ->{

                editor = sharedPref.edit();
             myEmail = emailAddress.getText().toString();
                editor.putString("email", myEmail);
                editor.commit();
            }
        );
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
}
