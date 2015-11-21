package com.uni.kevintruong.flextime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.adapters.GeoLocationAdapter;
import com.uni.kevintruong.flextime.managers.DatabaseManager;
import com.uni.kevintruong.flextime.models.GeoLocation;
import com.uni.kevintruong.flextime.models.Session;

import java.util.ArrayList;

public class MyLocationsActivity extends AppCompatActivity
{
    private ListView myLocationsListView;
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_locations);

        this.db = DatabaseManager.getInstance(this);

        GeoLocation[] geoLocationsArray = mapListToArray(this.db.getGeoLocations());
        ListAdapter geoLocationAdapter = new GeoLocationAdapter(this, geoLocationsArray);

        myLocationsListView = (ListView) findViewById(R.id.myLocationList);
        myLocationsListView.setAdapter(geoLocationAdapter);

        myLocationsListView.setOnItemClickListener(handleListViewItemClick());
    }

    private AdapterView.OnItemClickListener handleListViewItemClick()
    {
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                GeoLocation geoLocation = db.getGeoLocations().get(position);

                ArrayList<Session> sessions = geoLocation.getSessions();

                Intent sessionsIntent = new Intent("com.uni.kevintruong.flextime.SessionsActivity");
                sessionsIntent.putParcelableArrayListExtra("test", sessions);
                sessionsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(sessionsIntent);

                Toast.makeText(getBaseContext(), position + " " + parent.getItemAtPosition(position) + " is selected", Toast.LENGTH_SHORT).show();

            }
        };

        return onItemClickListener;
    }

    private GeoLocation[] mapListToArray(ArrayList<GeoLocation> geoLocations)
    {
        GeoLocation[] temp = new GeoLocation[geoLocations.size()];

        for (int i = 0; i < geoLocations.size(); i++)
        {
            temp[i] = geoLocations.get(i);
        }

        return temp;
    }
}
