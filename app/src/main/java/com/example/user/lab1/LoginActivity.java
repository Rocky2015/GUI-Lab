package com.example.user.lab1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "LoginActivity";
    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;
    private String myEmail;
     private Context context ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In OnCreate");
        setContentView(R.layout.activity_login);
        context = getApplicationContext();
        final EditText emailAddress = (EditText)findViewById(R.id.loginEmail);

        sharedPref = context.getSharedPreferences("prefsfile", MODE_PRIVATE);
        emailAddress.setText( sharedPref.getString("email", "email@domain.com") );

        Button button = (Button) findViewById(R.id.loginButton1);

        button.setOnClickListener(e ->{

            editor = sharedPref.edit();
            myEmail = emailAddress.getText().toString();
            editor.putString("email", myEmail);
            editor.commit();
            startActivity(new Intent( context, StartActivity.class ));
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
