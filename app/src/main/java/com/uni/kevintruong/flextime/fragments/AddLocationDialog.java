package com.uni.kevintruong.flextime.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
    private EditText latitude;
    private EditText longitude;
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
        latitude = (EditText) view.findViewById(R.id.addLocationLatitude);
        longitude = (EditText) view.findViewById(R.id.addLocationLongitude);
        locationName =  (EditText) view.findViewById(R.id.addLocationName);

        latitude.setText(String.valueOf(currentLocation.getLatitude()));
        longitude.setText(String.valueOf(currentLocation.getLongitude()));


        builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                DatabaseManager db = DatabaseManager.getInstance(getActivity());

                double lat = Double.parseDouble(String.valueOf(latitude.getText()));
                double lng = Double.parseDouble(String.valueOf(longitude.getText()));
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
