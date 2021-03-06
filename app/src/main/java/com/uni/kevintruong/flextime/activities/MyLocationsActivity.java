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
import com.uni.kevintruong.flextime.managers.GeoLocationManager;
import com.uni.kevintruong.flextime.managers.SessionManager;
import com.uni.kevintruong.flextime.models.GeoLocation;
import com.uni.kevintruong.flextime.models.Session;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Handles MyLocationsActivity
 */
public class MyLocationsActivity extends AppCompatActivity
{
    private GeoLocationManager _gm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_locations);

        _gm = new GeoLocationManager(this);

        //Converts arrayList to array
        GeoLocation[] geoLocationsArray = _gm.getGeoLocations().toArray(new GeoLocation[_gm.getGeoLocations().size()]);
        ListAdapter geoLocationAdapter = new GeoLocationAdapter(this, geoLocationsArray);

        ListView myLocationsListView = (ListView) findViewById(R.id.myLocationList);
        myLocationsListView.setAdapter(geoLocationAdapter);
        myLocationsListView.setOnItemClickListener(handleListViewItemClick());
    }

    /**
     * Set OnClickListener för listView
     * @return onItemClickListener
     */
    private AdapterView.OnItemClickListener handleListViewItemClick()
    {
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                GeoLocation geoLocation = _gm.getGeoLocations().get(position);
                SessionManager sm = new SessionManager(getApplicationContext());
                try
                {
                    geoLocation.setSessions(sm.getSessions(geoLocation.getId()));
                    ArrayList<Session> sessions = geoLocation.getSessions();
                    //Creates Inent to start the next activity, we alsot pass arraylist with sessions to the activity
                    Intent sessionsIntent = new Intent("com.uni.kevintruong.flextime.SessionsSingleActivity");
                    sessionsIntent.putParcelableArrayListExtra("sessions", sessions);
                    sessionsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(sessionsIntent);

                    Toast.makeText(getBaseContext(), position + " " + parent.getItemAtPosition(position) + " is selected", Toast.LENGTH_SHORT).show();
                } catch (ParseException e)
                {
                    e.printStackTrace();
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        };

        return onItemClickListener;
    }

}
