package com.uni.kevintruong.flextime.managers;

import android.content.Context;

import com.google.android.gms.location.Geofence;
import com.uni.kevintruong.flextime.helpers.Converter;
import com.uni.kevintruong.flextime.models.GeoLocation;

import java.util.ArrayList;

/**
 * Created by kwan8 on 2015-11-30.
 * Gets and adds Geoloaction to database and converts them to geoloaction objects.
 */
public class GeoLocationManager
{
    private Converter _converter;
    private DatabaseManager _db;

    public GeoLocationManager(Context context)
    {
        this._converter = new Converter();
        this._db = DatabaseManager.getInstance(context);
    }

    /**
     * Gets a GeoLocation by its name
     *
     * @param name geoLocation name
     * @return requested geoLocation
     */
    public GeoLocation getGeoLoactionByName(String name)
    {
        GeoLocation geoLocationTemp = null;

        for (GeoLocation geoLocation : getGeoLocations())
        {
            if (geoLocation.getName().equals(name))
            {
                geoLocationTemp = geoLocation;
            }
        }
        return geoLocationTemp;
    }

    public ArrayList<GeoLocation> getGeoLocations()
    {
        return _converter.convertToGeoLocations(_db.getGeoLocationsDb());
    }

    public ArrayList<Geofence> getGeofences()
    {
        return _converter.convertGeolocationsToGeofences(getGeoLocations());
    }

    public ArrayList<GeoLocation> deleteGeoLocation(int geoLocationId)
    {
        _db.deleteGeoLocationDb(geoLocationId);

        return getGeoLocations();
    }

    public void addGeoLocation(GeoLocation geoLocation)
    {
        _db.addGeoLocationDb(geoLocation);
    }
}
