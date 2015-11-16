package com.uni.kevintruong.flextime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.uni.kevintruong.flextime.R;

public class LocationActivity extends AppCompatActivity
{
    private TextView locationTitle;
    private EditText locationLatitude;
    private EditText locationLongitude;
    private EditText locationName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationTitle = (TextView) findViewById(R.id.locationTitle);
        locationName =  (EditText) findViewById(R.id.locationName);
        locationLatitude = (EditText) findViewById(R.id.locationLatitude);
        locationLongitude = (EditText) findViewById(R.id.locationLongitude);


    }
}
