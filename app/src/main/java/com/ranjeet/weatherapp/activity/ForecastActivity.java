package com.ranjeet.weatherapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.ranjeet.weatherapp.R;
import com.ranjeet.weatherapp.helper.Constants;
import com.ranjeet.weatherapp.helper.WeatherForecastHelper;
import com.ranjeet.weatherapp.helper.WeatherHelper;

import org.apache.http.Header;

/**
 * Created by Ranjeet Kushwaha on 07.01.2017
 */

public class ForecastActivity extends AppCompatActivity {

    Toolbar mToolbar;
    WeatherForecastHelper WeatherForecastHelper;
    WeatherHelper WeatherHelper;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //mToolbar.setNavigationIcon(R.drawable.ic_menu);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        WeatherForecastHelper = new WeatherForecastHelper();
        WeatherHelper = new WeatherHelper();
        getForecastData();
    }

    public void getForecastData(){
        /**
         * Get settings
         */
        String city = prefs.getString("location", Constants.location);
        String CountryCode = prefs.getString("countrykey", Constants.countrykey);
        String unit = prefs.getString("unitcode", Constants.unitcode);
        String language = prefs.getString("lang", Constants.lang);
        /**
         * Start JSON data download
         */
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + CountryCode + "&units=" + unit + "&lang=" + language, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                /**
                 * Only for testing
                 */
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                String in = new String(response);
                if (in != "") {
                    WeatherForecastHelper.ParseData(in);
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    getForecast();
                } else {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                /**
                 * Called when response HTTP status is "4XX" (eg. 401, 403, 404)
                 * Setting ScrollView's & LoadingLayout's visibility to gone -> displaying the ErrorLayout
                 * TODO Find a better solution for this
                 */
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }
    public void getForecast(){
        WeatherForecastHelper.getWeatherForecastForId(1);

        Toast.makeText(getApplicationContext(), WeatherHelper.convertTime(WeatherForecastHelper.date), Toast.LENGTH_SHORT).show();
    }

}

