package com.uni.kevintruong.flextime.managers;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import java.util.ArrayList;

/**
 * Created by kwan8 on 2015-11-07.
 * Handles geoFence
 */
public class GeofenceManager implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status>, LocationListener
{

    private static GeofenceManager instance;
    private final String TAG = this.getClass().getSimpleName();

    private Context _context;
    private GoogleApiClient _googleApiClient;
    private PendingIntent _pendingIntent;
    private ArrayList<Geofence> _geofences;
    private LocationRequest _locationRequest;

    public static synchronized GeofenceManager getInstance(Context context, ArrayList<Geofence> geofences)
    {
        if (instance == null)
        {
            instance = new GeofenceManager(context.getApplicationContext(), geofences);
        }
        return instance;
    }

    /**
     * Constructs a new GeofenceManager.
     * @param context   The context to use.
     * @param geofences List of geofences to monitor.
     */
    private GeofenceManager(Context context, ArrayList<Geofence> geofences)
    {
        _context = context;
        _geofences = new ArrayList<>(geofences);
        _pendingIntent = null;

        // Build a new GoogleApiClient, specify that we want to use LocationServices
        _googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        _locationRequest = new LocationRequest();
        //updates every 10 seconds.
        _locationRequest.setInterval(10000);
        //sets locationRequest accuracy.
        _locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        _googleApiClient.connect();
    }

    public void disconnect()
    {
        _googleApiClient.disconnect();
    }

    @Override
    public void onResult(Status result)
    {
        if (result.isSuccess())
        {
            Log.v(TAG, "Success!");
        } else if (result.hasResolution())
        {
            // TODO Handle resolution
        } else if (result.isCanceled())
        {
            Log.v(TAG, "Canceled");
        } else if (result.isInterrupted())
        {
            Log.v(TAG, "Interrupted");
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        Log.v(TAG, "Connection failed.");
    }

    @Override
    public void onConnected(Bundle connectionHint)
    {
        try
        {
            GeofencingRequest geofencingRequest = new GeofencingRequest.Builder().addGeofences(
                    _geofences).build();

            _pendingIntent = createRequestPendingIntent();

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    _googleApiClient, _locationRequest, this);

            PendingResult<Status> pendingResult = LocationServices.GeofencingApi
                    .addGeofences(_googleApiClient, geofencingRequest,
                            _pendingIntent);

            pendingResult.setResultCallback(this);

        } catch (IllegalArgumentException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int cause)
    {
        Log.v(TAG, "Connection suspended.");
    }

    /**
     * This creates a PendingIntent that is to be fired when geofence transitions
     * take place.
     *
     * @return A PendingIntent that will handle geofence transitions.
     */
    private PendingIntent createRequestPendingIntent()
    {
        if (_pendingIntent == null)
        {
            Log.v(TAG, "Creating PendingIntent");
            Intent intent = new Intent(_context, GeofenceIntentService.class);
            _pendingIntent = PendingIntent.getService(_context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

        return _pendingIntent;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        Log.v(TAG, "Location Information\n"
                + "==========\n"
                + "Provider:\t" + location.getProvider() + "\n"
                + "Lat & Long:\t" + location.getLatitude() + ", "
                + location.getLongitude() + "\n"
                + "Altitude:\t" + location.getAltitude() + "\n"
                + "Bearing:\t" + location.getBearing() + "\n"
                + "Speed:\t\t" + location.getSpeed() + "\n"
                + "Accuracy:\t" + location.getAccuracy() + "\n");
    }
}
