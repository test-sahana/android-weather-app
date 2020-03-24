package edu.cuny.qc.cs.weatherchannel;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.testfairy.TestFairy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class weatherchannel extends Activity implements OnClickListener, OnItemSelectedListener {
    private static final String TAG = "WeatherChannel";

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    private EditText weatherList_textField1;
    private EditText weatherListIndex_textField1;
    private EditText weatherListItem_textField1;

    private Button submit_getWeatherInfo;

    private String[] weatherInforList;
    private String queryString = "";

    private Spinner spinner;
    private String[] items = {
            "https://weather.com/weather/today/l/11367:4:US",
            "http://www.google.com/search?q="
    };

    String urlString = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TestFairy.begin(this, "71913e63eb0a32ec95b56c63accff7a9ea8bc4e6");

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        editor = settings.edit();

        setContentView(R.layout.activity_weatherchannel);

        weatherListItem_textField1 = (EditText)findViewById(R.id.editText1_weatherItem);
        weatherListItem_textField1.setBackgroundColor(Color.GRAY);
        weatherListItem_textField1.setTextColor(Color.parseColor("#000000"));

        weatherListItem_textField1.setText( settings.getString("selection_queryString", "") );

        submit_getWeatherInfo = (Button)findViewById(R.id.button_getWeatherInfo);
        submit_getWeatherInfo.setBackgroundColor(Color.LTGRAY);
        submit_getWeatherInfo.setOnClickListener(this);

        spinner = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(weatherchannel.this,
                android.R.layout.simple_spinner_item,items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
        urlString = (String) parent.getItemAtPosition(0);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                urlString = (String) parent.getItemAtPosition(0);
                break;
            case 1:
                urlString = (String) parent.getItemAtPosition(1);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        String validateResult;

        if (v == submit_getWeatherInfo) {
                String query = "";
                String searchString = weatherListItem_textField1.getText().toString();
                if (urlString.equals("http://www.google.com/search?q=")) {
                    // do something .... searchString = searchString.trim().replaceAll("\\s+", " ").replace(" ", "+");
                }

                query = urlString + searchString;
                Log.e(TAG, query);

                URL url;
                try {
                    url = new URL(query);

                    WebView myWebView = (WebView) findViewById(R.id.webview);
                    myWebView.getSettings().setJavaScriptEnabled(true);
                    myWebView.clearCache(true);
                    myWebView.loadUrl(url.toString());

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

        }

    }

}

