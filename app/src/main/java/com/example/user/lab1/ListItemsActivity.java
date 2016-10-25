package com.example.user.lab1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;


public class ListItemsActivity extends AppCompatActivity {
    private final String ACTIVITY_NAME = "ListItemsActivity";
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton imgButton;
    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In OnCreate");
        setContentView(R.layout.activity_list_items);

        imgButton = (ImageButton) findViewById(R.id.cameraButton);
        imgButton.setOnClickListener(e -> {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        aSwitch = (Switch) findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (aSwitch.isChecked()) {
                Toast toast = Toast.makeText(ListItemsActivity.this, "Switch is On", Toast.LENGTH_SHORT);//this is the ListActivity
                toast.show();//display your message box
            } else {
                Toast toast = Toast.makeText(ListItemsActivity.this, "Switch is Off", Toast.LENGTH_SHORT);//this is the ListActivity
                toast.show();//display your message box
            }
        });
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnClickListener(e -> {

            AlertDialog.Builder confirmBox = new AlertDialog.Builder(ListItemsActivity.this);
            // 2. Chain together various setter methods to set the dialog characteristics
            confirmBox.setMessage(R.string.dialog_message);//Add a dialog message to strings.xml
            confirmBox.setTitle(R.string.dialog_Title);
            confirmBox.setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) (dialog, id) -> {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Response", "My information to share");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            });
            confirmBox.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) (dialog, id) -> {
        // User cancelled the dialog
    });
            confirmBox.show();


        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgButton.setImageBitmap(imageBitmap);
        }
    }
}
