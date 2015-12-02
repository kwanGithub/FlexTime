package com.uni.kevintruong.flextime.managers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by kwan8 on 2015-11-01.
 * Handles device gps and network location services
 */
public class UnitManager extends Service implements LocationListener
{
    private static UnitManager instance;
    private Context _context;

    protected LocationManager locationManager;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    public static UnitManager getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new UnitManager(context.getApplicationContext());
        }
        return instance;
    }

    private UnitManager(Context context)
    {
        _context = context;
    }

    /**
     * Gets current location from the unit
     */
    public Location getLocation()
    {
        Location location = null;
        //Checks if gps and network of the device is available
        try
        {
            locationManager = (LocationManager) _context.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isNetworkEnabled && !isGPSEnabled)
            {
                //TODO: ALERT DIALOG FOR USER
            } else
            {
                this.canGetLocation = true;

                if (isNetworkEnabled)
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null)
                    {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }

                if (isGPSEnabled)
                {
                    if (location == null)
                    {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        if (locationManager != null)
                        {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void onLocationChanged(Location location){}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){}

    @Override
    public void onProviderEnabled(String provider){}

    @Override
    public void onProviderDisabled(String provider){}

    @Nullable
    @Override
    public IBinder onBind(Intent intent){ return null;
    }
}
