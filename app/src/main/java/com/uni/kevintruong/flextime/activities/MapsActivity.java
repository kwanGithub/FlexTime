package com.uni.kevintruong.flextime.activities;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.managers.GeoLocationManager;
import com.uni.kevintruong.flextime.managers.GeofenceManager;
import com.uni.kevintruong.flextime.managers.UnitManager;

/**
 * Handles MapsActivity, draws googlemap, places out geofences on the map
 */
public class MapsActivity extends FragmentActivity implements OnCameraChangeListener
{
    private GoogleMap _googleMap;
    private GeoLocationManager _gm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        _gm = new GeoLocationManager(this);
        getCurrentLocation();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onStop()
    {
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

    /**
     * Repaints circles
     * @param position
     */
    @Override
    public void onCameraChange(CameraPosition position)
    {
        // Makes sure the visuals remain when zoom changes.
        for (int i = 0; i < _gm.getGeoLocations().size(); i++)
        {
            _googleMap.addCircle(new CircleOptions().center(_gm.getGeoLocations().get(i).getCoordinates())
                    .radius(_gm.getGeoLocations().get(i).getRadius())
                    .fillColor(0x40ff0000)
                    .strokeColor(Color.TRANSPARENT).strokeWidth(2));
        }
    }

    /**
     * Gets current location
     * @return current location
     */
    private Location getCurrentLocation()
    {
        Location location = GeofenceManager.getInstance(this).getLastKnownLocation();

        if (location == null)
        {
            location = UnitManager.getInstance(this).getLocation();
        }
        return location;
    }

    /**
     * Obtains a googleMap
     */
    private void setUpMapIfNeeded()
    {
        if (_googleMap == null)
        {
            _googleMap = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map)).getMap();

            if (_googleMap != null)
            {
                setUpMap();
            }
        }
    }

    /**
     * Creates a googleMap
     */
    private void setUpMap()
    {
        LatLng currentLocation = new LatLng(getCurrentLocation().getLatitude(), getCurrentLocation().getLongitude());
        _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14));

        _googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        _googleMap.setIndoorEnabled(false);
        _googleMap.setMyLocationEnabled(true);
        _googleMap.setOnCameraChangeListener(this);
    }
}
