package com.ranjeet.weatherapp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.ranjeet.weatherapp.R;
import com.ranjeet.weatherapp.adapter.ForecastOverviewAdapter;
import com.ranjeet.weatherapp.helper.Constants;
import com.ranjeet.weatherapp.model.Day;

import org.apache.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ranjeet Kushwaha on 07.01.2017
 */
public class DailyForecastActivity extends BaseActivity {

    public ArrayList<Day> items;
    ForecastOverviewAdapter adapter;
    String city, CountryCode, language, unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTheme();
        setContentView(R.layout.testlayout);
        setupToolbar();
        setToolbarBackIcon();
        items = new ArrayList<>();
        downloadJSON();

        RecyclerView rvUsers = (RecyclerView) findViewById(R.id.rvUsers);
        adapter = new ForecastOverviewAdapter(this, getDays());
        rvUsers.setAdapter(adapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<Day> getDays() {
        return items;
    }

    public void downloadJSON() {
        /**
         * Get settings
         */
        city = SharedPreferences.getString("location", Constants.location);
        CountryCode = SharedPreferences.getString("countrykey", Constants.countrykey);
        unit = SharedPreferences.getString("unitcode", Constants.unitcode);
        language = SharedPreferences.getString("lang",Constants.lang);
        /**
         * Start JSON data download
         */
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://api.openweathermap.org/data/2.5/forecast/daily?q=" + city + "," + CountryCode + "&units=" + unit + "&lang=" + language + "&cnt=" + SharedPreferences.getString("days", "14") + "&APPID=" + ApiKey, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                String in = new String(response);
                ParseData(in);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(DailyForecastActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    public void ParseData(String in) {
        try {
            JSONObject reader = new JSONObject(in);
            JSONArray list = reader.getJSONArray("list");
            JSONObject JSONList = list.getJSONObject(0);
            for (int i = 0; i < list.length(); i++) {
                JSONObject forJSONList = list.getJSONObject(i);
                JSONArray forWeather = forJSONList.getJSONArray("weather");
                JSONObject forJSONWeather = forWeather.getJSONObject(0);
                JSONObject fortemp = forJSONList.getJSONObject("temp");
                Log.i("RecyclerView", "JSON Parsing Nr." + i);
                items.add(new Day(forJSONList.getInt("dt"), fortemp.getDouble("max"), fortemp.getDouble("min"), forJSONList.getDouble("pressure"), forJSONList.getInt("humidity"), forJSONWeather.getInt("id"), forJSONWeather.getString("description"), forJSONList.getDouble("speed")));
                Log.i("RecyclerView", "Added items Nr" + i);
                adapter.notifyItemInserted(0);
                Log.i("RecyclerView", "notifyItemInserted Nr." + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("JSON Parsing", e.toString());
        }
    }
}
