package com.uni.kevintruong.flextime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.managers.DatabaseManager;
import com.uni.kevintruong.flextime.models.GeoLocation;

import java.util.ArrayList;
import java.util.List;

public class MyLocationsActivity extends AppCompatActivity
{
    private ListView myLocationsListView;
    private List<String> myLocations;
    private ArrayAdapter<String> adapter;
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_locations);

        this.db = DatabaseManager.getInstance(this);
        myLocations = getLocationNames(this.db.getGeoLocations());


        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, myLocations);

        myLocationsListView = (ListView) findViewById(R.id.myLocationList);
        myLocationsListView.setAdapter(adapter);

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
                Bundle bundle = new Bundle();
                bundle.putParcelable("GeoLocation", geoLocation);

                Intent locationIntent = new Intent("com.uni.kevintruong.flextime.LocationActivity");
                locationIntent.putExtras(bundle);
                locationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(locationIntent);

                Toast.makeText(getBaseContext(), position + " " + parent.getItemAtPosition(position) + " is selected", Toast.LENGTH_SHORT).show();

            }
        };

        return onItemClickListener;
    }

    private ArrayList<String> getLocationNames(ArrayList<GeoLocation> geoLocations)
    {
        ArrayList<String> temp = new ArrayList<>();

        for(GeoLocation location : geoLocations)
        {
            temp.add(location.getName());
        }

        return temp;
    }
}
