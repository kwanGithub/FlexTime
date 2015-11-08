package com.uni.kevintruong.flextime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.managers.DatabaseManager;

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
        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, db.getMyLocationsTestData());

        myLocationsListView = (ListView) findViewById(R.id.myLocationList);
        myLocationsListView.setAdapter(adapter);

        myLocationsListView.setOnItemClickListener(handleItemClick());
    }

    private AdapterView.OnItemClickListener handleItemClick()
    {
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " is selected", Toast.LENGTH_SHORT).show();
            }
        };

        return onItemClickListener;
    }
}
