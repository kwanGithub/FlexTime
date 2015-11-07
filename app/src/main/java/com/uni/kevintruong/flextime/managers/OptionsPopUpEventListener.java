package com.uni.kevintruong.flextime.managers;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.uni.kevintruong.flextime.R;

/**
 * Created by kwan8 on 2015-11-07.
 */
public class OptionsPopUpEventListener implements PopupMenu.OnMenuItemClickListener
{
    Context context;

    public OptionsPopUpEventListener(Context context)
    {
        this.context = context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.option_Pause:
                Toast.makeText(this.context, "Pause", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.option_CurrentPosition:
                Toast.makeText(this.context, "Monitor Current Location", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.option_AddPositionByCoordinates:
                Toast.makeText(this.context, "Monitor Location by Coordinates", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.option_MyLocations:
                //TODO CONTEXT MENU LIST VIEW
                Toast.makeText(this.context, "My Locations", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.option_Map:
                Toast.makeText(this.context, "Map selected", Toast.LENGTH_LONG).show();
                Intent intent = new Intent("com.uni.kevintruong.flextime.MapsActivity");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.context.startActivity(intent);
                return true;
        }
        return false;
    }
}
