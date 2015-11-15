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
import com.uni.kevintruong.flextime.fragments.AddLocationDialog;

public class MainActivity extends AppCompatActivity
{
    private Button optionsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OnClickOptionsBtnListener();
    }

    public void OnClickOptionsBtnListener()
    {
        optionsBtn = (Button) findViewById(R.id.mainOptionsBtn);

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

    public void showOptionsMenuPopUp(View view)
    {
        PopupMenu optionsPopupMenu = new PopupMenu(this, view);
        MenuInflater menuInflater = optionsPopupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.options_popup_menu, optionsPopupMenu.getMenu());
        optionsPopupMenu.setOnMenuItemClickListener(handlePopupMenuClick());
        optionsPopupMenu.show();
    }

    private void addLocationDialog()
    {
        AddLocationDialog addLocationDialog = new AddLocationDialog();
        addLocationDialog.show(getFragmentManager(), "Add Location Dialog");
    }

    private PopupMenu.OnMenuItemClickListener handlePopupMenuClick()
    {
        PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.option_Pause:
                        Toast.makeText(getApplicationContext(), "Pause", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.option_CurrentPosition:
                        Toast.makeText(getApplicationContext(), "Monitor Current Location", Toast.LENGTH_SHORT).show();
                        addLocationDialog();
                        return true;
                    case R.id.option_AddPositionByCoordinates:
                        //TODO SHOW 2 INPUT DIALOGS ONE FOR LATITUDE AND ONE FOR LONGITUDE
                        Toast.makeText(getApplicationContext(), "Monitor Location by Coordinates", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.option_MyLocations:
                        //TODO ON ITEM CLICK SHOW LOCATION DATA AND STAT
                        Toast.makeText(getApplicationContext(), "My Locations", Toast.LENGTH_SHORT).show();
                        Intent myLocationsIntent = new Intent("com.uni.kevintruong.flextime.MyLocationsActivity");
                        myLocationsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(myLocationsIntent);
                        return true;
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
