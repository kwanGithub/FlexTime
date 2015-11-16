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
import com.uni.kevintruong.flextime.managers.DatabaseManager;
import com.uni.kevintruong.flextime.managers.UnitLocationManager;

/**
 * Created by kwan8 on 2015-11-15.
 */
public class AddLocationDialog extends DialogFragment
{

    private LayoutInflater inflater;
    private View view;
    private TextView locationTitle;
    private EditText locationLatitude;
    private EditText locationLongitude;
    private EditText locationName;
    private UnitLocationManager unitLocationManager;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        unitLocationManager = UnitLocationManager.getInstance(getActivity());
        Location currentLocation = unitLocationManager.getUnitLocation();
        this.inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_add_location, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        locationTitle = (TextView) view.findViewById(R.id.locationTitle);
        locationName =  (EditText) view.findViewById(R.id.locationName);
        locationLatitude = (EditText) view.findViewById(R.id.locationLatitude);
        locationLongitude = (EditText) view.findViewById(R.id.locationLongitude);

        locationTitle.setText("Add current location or custom location by editing latitude and longitude values");
        locationName.setHint("Enter Location Name");
        locationLatitude.setHint("Enter Latitude");
        locationLongitude.setHint("Enter Longitude");

        locationLatitude.setText(String.valueOf(currentLocation.getLatitude()));
        locationLongitude.setText(String.valueOf(currentLocation.getLongitude()));

        builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                DatabaseManager db = DatabaseManager.getInstance(getActivity());

                double lat = Double.parseDouble(String.valueOf(locationLatitude.getText()));
                double lng = Double.parseDouble(String.valueOf(locationLongitude.getText()));
                db.addGeoLocations(db.getGeoLocations().size() + 1, locationName.getText().toString(), lat, lng);
                Toast.makeText(getActivity(),locationName.getText().toString() + " Added" , Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        return builder.create();
    }
}
