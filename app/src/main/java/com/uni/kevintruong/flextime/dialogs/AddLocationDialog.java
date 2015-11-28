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
        UnitManager unitManager = UnitManager.getInstance(getActivity());
        Location currentLocation = unitManager.getUnitLocation();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_location, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        TextView locationTitle = (TextView) view.findViewById(R.id.locationTitle);
        _locationName =  (EditText) view.findViewById(R.id.locationName);
        _locationLatitude = (EditText) view.findViewById(R.id.locationLatitude);
        _locationLongitude = (EditText) view.findViewById(R.id.locationLongitude);

        locationTitle.setText("Add current location or custom location by editing latitude and longitude values");
        _locationName.setHint("Enter Location Name");
        _locationLatitude.setHint("Enter Latitude");
        _locationName.setHint("Enter Longitude");

        _locationLatitude.setText(String.valueOf(currentLocation.getLatitude()));
        _locationName.setText(String.valueOf(currentLocation.getLongitude()));

        builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                DatabaseManager db = DatabaseManager.getInstance(getActivity());

                double lat = Double.parseDouble(String.valueOf(_locationLatitude.getText()));
                double lng = Double.parseDouble(String.valueOf(_locationName.getText()));
                db.addGeoLocation(new GeoLocation(db.getGeoLocations().size() + 1, _locationName.getText().toString(), lat, lng, 100));
                Toast.makeText(getActivity(),"Added " + db.databaseToString() , Toast.LENGTH_LONG).show();
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
