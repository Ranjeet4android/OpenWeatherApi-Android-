package com.ranjeet.weatherapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.ranjeet.weatherapp.R;

/**
 * Created by Ranjeet Kushwaha on 07.01.2017
 */
public class AboutActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView tx = (TextView) findViewById(R.id.appdesc);
        tx.setText(Html.fromHtml(getResources().getString(R.string.versionDesc)));
        findViewById(R.id.button_license).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, LicenseActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new AlertDialog.Builder(this)
                .setTitle("Open Weather App")
                .setMessage("This is testing version of App")
                .setPositiveButton(android.R.string.ok, null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cancel, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Shows the debug warning, if this is a debug build and the warning has not been shown yet

    }
}
