package com.uni.kevintruong.flextime.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.managers.GeoLocationManager;
import com.uni.kevintruong.flextime.managers.GeofenceManager;
import com.uni.kevintruong.flextime.managers.UnitManager;
import com.uni.kevintruong.flextime.models.GeoLocation;

/**
 * Created by kwan8 on 2015-11-15.
 * Handles the addLocation dialog
 */
public class AddLocationDialog extends DialogFragment
{
    private EditText _locationLatitude;
    private EditText _locationLongitude;
    private EditText _locationName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Location currentLocation;

        //if Googleapis client haven't stored or activated lastKnownLocation we use the units gps. As soon as it it active we will use googleapi
        if (GeofenceManager.getInstance(getActivity().getApplicationContext()).getLastKnownLocation() == null)
        {
            currentLocation = UnitManager.getInstance(getActivity().getApplicationContext()).getLocation();
        } else
        {
            currentLocation = GeofenceManager.getInstance(getActivity().getApplicationContext()).getLastKnownLocation();
        }

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_location, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        TextView locationTitle = (TextView) view.findViewById(R.id.locationTitle);
        _locationName = (EditText) view.findViewById(R.id.locationName);
        _locationLatitude = (EditText) view.findViewById(R.id.locationLatitude);
        _locationLongitude = (EditText) view.findViewById(R.id.locationLongitude);

        locationTitle.setText("Add current location or custom location by editing latitude and longitude values");
        _locationName.setHint("Enter Location Name");
        _locationLatitude.setHint("Enter Latitude");
        _locationLongitude.setHint("Enter Longitude");

        _locationLatitude.setText(String.valueOf(currentLocation.getLatitude()));
        _locationLongitude.setText(String.valueOf(currentLocation.getLongitude()));

        builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                GeoLocationManager gm = new GeoLocationManager(getActivity().getApplicationContext());

                double lat = Double.parseDouble(String.valueOf(_locationLatitude.getText()));
                double lng = Double.parseDouble(String.valueOf(_locationLongitude.getText()));
                gm.addGeoLocation(new GeoLocation(gm.getGeoLocations().size() + 1, _locationName.getText().toString(), lat, lng, 100));

                Toast.makeText(getActivity().getApplicationContext(), "Added ", Toast.LENGTH_LONG).show();

                //Starts monitoring after we have our first location
                GeofenceManager.getInstance(getActivity().getApplicationContext()).addGeoFences(gm.getGeofences());
                GeofenceManager.getInstance(getActivity().getApplicationContext()).connectGoogleApi();
            }
        }).setNegativeButton("CANCEL", null);

        return builder.create();
    }
}
