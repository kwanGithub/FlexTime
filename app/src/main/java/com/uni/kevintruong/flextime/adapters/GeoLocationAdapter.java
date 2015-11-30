package com.uni.kevintruong.flextime.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.managers.GeoLocationManager;
import com.uni.kevintruong.flextime.models.GeoLocation;

/**
 * Created by kwan8 on 2015-11-17.
 * Handles and Converts GeoLocation data to listRow and the deleting for GeoLocation
 */
public class GeoLocationAdapter extends ArrayAdapter<GeoLocation>
{
    public GeoLocationAdapter(Context context, GeoLocation[] resource)
    {
        super(context, R.layout.session_row, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View locationView = layoutInflater.inflate(R.layout.geolocation_row, parent, false);

        GeoLocation singleGeoLocation = getItem(position);
        TextView locationName = (TextView) locationView.findViewById(R.id.locationName);
        locationName.setText(singleGeoLocation.getName());

        Button editBtn = (Button) locationView.findViewById(R.id.geoLocationDelete);
        editBtn.setOnClickListener(deleteBtnOnClickListener(getContext(), singleGeoLocation));

        return locationView;
    }

    private View.OnClickListener deleteBtnOnClickListener(final Context context, GeoLocation geoLocation)
    {
        final Context tcontext = context;
        final GeoLocation currentGeoLocation = geoLocation;

        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new AlertDialog.Builder(tcontext)
                        .setTitle("Delete")
                        .setMessage(currentGeoLocation.getName() + " ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                GeoLocationManager gm = new GeoLocationManager(context);
                                gm.deleteGeoLocation(currentGeoLocation.getId());
                                Toast.makeText(tcontext, "Deleted " + currentGeoLocation.getName(), Toast.LENGTH_SHORT).show();
                                //Reloads list by restarting myLocations activity
                                Intent myLocationsIntent = new Intent("com.uni.kevintruong.flextime.MyLocationsActivity");
                                myLocationsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myLocationsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                tcontext.startActivity(myLocationsIntent);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        };
    }
}
