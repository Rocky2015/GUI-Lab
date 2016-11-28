package com.example.user.lab1;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
    TextView textView1, textView2, textView3;
    ProgressBar progBar;
    ImageView imageView;
    String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


        progBar = (ProgressBar) findViewById(R.id.progressBar);

        imageView = (ImageView) findViewById(R.id.weatherView);

        progBar.setMax(3);
        progBar.setVisibility(View.VISIBLE);
        new ForecastQuery().execute(urlString);


    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        private int state;
        String icon;


        protected String doInBackground(String... params) {
            state = 0;
            try {

                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xpp.setInput(urlConnection.getInputStream(), null);
                xpp.nextTag();

                xpp.setInput(inputStream, "UTF8");

                int eventType = XmlPullParser.START_DOCUMENT;

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    switch (eventType) {

                        case XmlPullParser.START_DOCUMENT:
                            break;

                        case XmlPullParser.END_DOCUMENT:
                            break;

                        case XmlPullParser.START_TAG:
                            String name = xpp.getName();
                            if (name.equals("temperature")) {
                                String value = xpp.getAttributeValue(null, "value=");
                                String min = xpp.getAttributeValue(null, "min=");
                                String max = xpp.getAttributeValue(null, "max=");

                                publishProgress(state += 25, state += 50, state += 75);
                            }
                            if (name.equals("weather")) {
                                icon = xpp.getAttributeValue(null, "icon=");

                                FileOutputStream outputStream;
                                publishProgress(state += 100);
                                switch (xpp.getAttributeValue(1)) {
                                    case "sunny":
                                        try {
                                            fileName = "sunny";
                                            File file = new File(fileName);
                                            if (!fileExistance(fileName)) {
                                                HTTPUtils image = new HTTPUtils();
                                                outputStream = openFileOutput(fileName + ".png", Context.MODE_PRIVATE);
                                                image.getImage("http://openweathermap.org/img/w/" + icon + ".jpg").compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                                outputStream.close();
                                            }
                                        } catch (Exception e) {
                                            Toast.makeText(getBaseContext(), "FileNotFound", Toast.LENGTH_LONG);
                                            e.printStackTrace();
                                        }
                                        break;
                                }
                            }
                            break;
                    }
                }

            } catch (
                    Exception e
                    )

            {
                Log.e("XML PARSING", e.getMessage());
            }


            return null;
        }

        public boolean fileExistance(String fileName) {
            File file = getBaseContext().getFileStreamPath(fileName);
            return file.exists();
        }

        public void onProgressUpdate(Integer... value) {
            progBar.setVisibility(View.VISIBLE);
            switch (state++) {
                case 0:
                    textView1.setText(value[0]);
                    break;
                case 1:
                    textView2.setText(value[0]);
                    break;
                case 2:
                    textView3.setText(value[0]);
                    break;
            }
            progBar.setProgress(state);

        }
    }

    public class HTTPUtils {

        String imgUrlString = "http://openweathermap.org/img/w/" + fileName + ".png";

        public Bitmap getImage(String imgUrlString) {
            try {
                URL url = new URL(imgUrlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }
    }

    protected void onPostExecute(String result) {

        textView1 = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView1);
        textView3 = (TextView) findViewById(R.id.textView2);

        String[] ss = result.split(" ");
        textView1.setText("Min: " + ss[0]);
        textView2.setText("Max: " + ss[1]);
        textView3.setText("Current: " + ss[2]);
        try {
            fileName = ss[4];
            switch (fileName) {
                case "broken":
                    fileName = "Broken";
                    break;
                case "sunny":
                    fileName = "Sunny";
                    break;
                case "overcast":
                    fileName = "Overcast";
                    break;
                case "clear":
                    fileName = "Clear";
                    break;
            }
            FileInputStream inputStream;
            inputStream = openFileInput(fileName);
            imageView.setImageBitmap(BitmapFactory.decodeStream(inputStream));
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
