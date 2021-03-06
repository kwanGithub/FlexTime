package com.uni.kevintruong.flextime.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.dialogs.AddLocationDialog;
import com.uni.kevintruong.flextime.managers.GeoLocationManager;
import com.uni.kevintruong.flextime.managers.GeofenceManager;

/**
 * Handles main activity of the app. Directs the user to other activities via the optionbutton
 */
public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GeoLocationManager gm = new GeoLocationManager(this);

        //Dont start monitoring if we dont have any Location stored
        if(gm.getGeoLocations().size() != 0)
        {
            GeofenceManager.getInstance(this).addGeoFences(gm.getGeofences());
            GeofenceManager.getInstance(this).connectGoogleApi();
        }

        OnClickOptionsBtnListener();
    }

    /**
     * OnClickLister for option button
     */
    public void OnClickOptionsBtnListener()
    {
        Button optionsBtn = (Button) findViewById(R.id.mainOptionsBtn);

        optionsBtn.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                showOptionsMenuPopUp(v);
                return false;
            }
        });
    }

    /**
     * Displays option menu for the user
     *
     * @param view the view you want to display the menu for
     */
    public void showOptionsMenuPopUp(View view)
    {
        PopupMenu optionsPopupMenu = new PopupMenu(this, view);
        MenuInflater menuInflater = optionsPopupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.options_popup_menu, optionsPopupMenu.getMenu());
        optionsPopupMenu.setOnMenuItemClickListener(handlePopupMenuClick());
        optionsPopupMenu.show();
    }

    /**
     * Displays the addLocationDialog for the user
     */
    private void addLocationDialog()
    {
        AddLocationDialog addLocationDialog = new AddLocationDialog();
        addLocationDialog.show(getFragmentManager(), "Add Location Dialog");
    }

    /**
     * Listner for handling the options that are provided for the user     *
     * @return PopMenuOnMenuItemOnClickListener
     */
    private PopupMenu.OnMenuItemClickListener handlePopupMenuClick()
    {
        PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    // No functionality implemented for prototype
                    case R.id.option_Pause:
                        Toast.makeText(getApplicationContext(), "Stop Monitoring  ! Function not implemented yet!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.option_AddLocation:
                        //Starts a dialog for the user to add a GeoLocation
                        Toast.makeText(getApplicationContext(), "Add Location", Toast.LENGTH_SHORT).show();
                        addLocationDialog();
                        return true;
                    //Starts MyLocations activity
                    case R.id.option_MyLocations:
                        Toast.makeText(getApplicationContext(), "My Locations", Toast.LENGTH_SHORT).show();
                        Intent myLocationsIntent = new Intent("com.uni.kevintruong.flextime.MyLocationsActivity");
                        myLocationsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(myLocationsIntent);
                        return true;
                    //Starts Maps activity
                    case R.id.option_Map:
                        Toast.makeText(getApplicationContext(), "Map", Toast.LENGTH_LONG).show();
                        Intent mapIntent = new Intent("com.uni.kevintruong.flextime.MapsActivity");
                        mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(mapIntent);
                        return true;
                }
                return false;
            }
        };

        return onMenuItemClickListener;
    }
}
