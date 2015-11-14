package com.uni.kevintruong.flextime.managers;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

/**
 * Created by kwan8 on 2015-11-14.
 */

//TODO Fixa en getLocation metod för att retunerar location och ta bort klassen GpsManager
public class GoogleApiClientManager implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status>, LocationListener
{

    private static GoogleApiClientManager instance;
    private GoogleApiClient googleApiClient;
    private Location location;
    private Context context;

    public static synchronized GoogleApiClientManager getGoogleApiClientManager(Context context)
    {
        if (instance == null)
        {
            instance = new GoogleApiClientManager(context.getApplicationContext());
        }

        return instance;
    }

    private GoogleApiClientManager(Context context)
    {
        this.context = context;
        this.googleApiClient = buildGoogleApiClient(this.context);
        connectGoogleApiClient();
    }

    private void connectGoogleApiClient()
    {
        if(this.googleApiClient == null)
        {
            this.googleApiClient.connect();
        }
    }

    private GoogleApiClient buildGoogleApiClient(Context context)
    {
        return new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void geeLocation()
    {

    }

    @Override
    public void onConnected(Bundle bundle)
    {
        this.location = LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);

        //DEBUG
        if(this.location != null)
        {

        }
    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onLocationChanged(Location location)
    {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {

    }

    @Override
    public void onResult(Status status)
    {

    }
}
