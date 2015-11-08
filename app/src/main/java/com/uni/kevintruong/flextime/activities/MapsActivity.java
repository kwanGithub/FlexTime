package com.uni.kevintruong.flextime.activities;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.managers.DatabaseManager;
import com.uni.kevintruong.flextime.managers.GpsManager;
import com.uni.kevintruong.flextime.models.GeoLocation;
import com.uni.kevintruong.flextime.models.GeofenceStore;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnCameraChangeListener
{

    private GoogleMap mMap;
    private GpsManager gpsManager;
    private Location currentLocation;
    private GeofenceStore geofenceStore;
    private ArrayList<Geofence> geofences;
    private ArrayList<GeoLocation> geoLocations;
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Initialize properties
        this.geofences = new ArrayList<>();
        this.geoLocations = new ArrayList<>();
        this.gpsManager = new GpsManager(getApplicationContext());
        this.currentLocation = gpsManager.getLocation();
        this.db = DatabaseManager.getInstance(this);
        //DEBUG
        this.geoLocations = db.getTestData();

        for (int i = 0; i < geoLocations.size(); i++)
        {
            geofences.add(mapDataToGeofence(geoLocations.get(i)));
        }
        this.geofenceStore = new GeofenceStore(this, this.geofences);
    }

    private Geofence mapDataToGeofence(GeoLocation geoLocation)
    {
        return new Geofence.Builder()
                .setRequestId(geoLocation.getName())
                .setCircularRegion(geoLocation.getCoordinates().latitude, geoLocation.getCoordinates().longitude, geoLocation.getRadius())
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setLoiteringDelay(3000)
                .setTransitionTypes(
                        Geofence.GEOFENCE_TRANSITION_ENTER
                                | Geofence.GEOFENCE_TRANSITION_DWELL
                                | Geofence.GEOFENCE_TRANSITION_EXIT).build();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        // mGeofenceStore.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS)
        {
            setUpMapIfNeeded();
        } else
        {
            GooglePlayServicesUtil.getErrorDialog(
                    GooglePlayServicesUtil.isGooglePlayServicesAvailable(this),
                    this, 0);
        }
    }

    private void setUpMapIfNeeded()
    {
        // Do a null check to confirm that we have not already instantiated the
        // map.
        if (mMap == null)
        {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map)).getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null)
            {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the
     * camera. In this case, we just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap}
     * is not null.
     */
    private void setUpMap()
    {
        // Centers the camera over the building and zooms int far enough to
        // show the floor picker.
        LatLng currentLocation = new LatLng(this.currentLocation.getLatitude(), this.currentLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14));

        // Hide labels.
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setIndoorEnabled(false);
        mMap.setMyLocationEnabled(true);
        mMap.setOnCameraChangeListener(this);
    }

    @Override
    public void onCameraChange(CameraPosition position)
    {
        // Makes sure the visuals remain when zoom changes.
        for (int i = 0; i < this.geoLocations.size(); i++)
        {
            mMap.addCircle(new CircleOptions().center(this.geoLocations.get(i).getCoordinates())
                    .radius(this.geoLocations.get(i).getRadius())
                    .fillColor(0x40ff0000)
                    .strokeColor(Color.TRANSPARENT).strokeWidth(2));
        }
    }
}
