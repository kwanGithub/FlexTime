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
import com.uni.kevintruong.flextime.managers.GeofenceManager;
import com.uni.kevintruong.flextime.managers.SessionManager;
import com.uni.kevintruong.flextime.managers.TransitionManager;
import com.uni.kevintruong.flextime.models.GeoLocation;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by kwan8 on 2015-11-17.
 * Handles and Converts GeoLocation data to listRow and removes geofences
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
        final GeoLocation currentGeoLocation = geoLocation;
        final Calendar cl = new GregorianCalendar();
        final SessionManager sm = new SessionManager(getContext());

        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage(currentGeoLocation.getName())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                GeoLocationManager gm = new GeoLocationManager(context);
                                //Ends session, if there is one active before deleting
                                if ( TransitionManager.getInstance().getCurrentSession() != null)
                                {
                                    sm.addSession( TransitionManager.getInstance().endSession(cl.getTime()));
                                    TransitionManager.getInstance().setCurrentSession(null);
                                }
                                gm.deleteGeoLocation(currentGeoLocation.getId());
                                notifyDataSetChanged();
                                Toast.makeText(context, "Deleted " + currentGeoLocation.getName(), Toast.LENGTH_SHORT).show();
                                GeofenceManager gfm = GeofenceManager.getInstance(context);
                                gfm.removeGeofence(currentGeoLocation.getName());

                                //Reloads list by restarting myLocations activity
                                Intent myLocationsIntent = new Intent("com.uni.kevintruong.flextime.MyLocationsActivity");
                                myLocationsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myLocationsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(myLocationsIntent);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        };
    }
}
