package com.uni.kevintruong.flextime.helpers;

import android.database.Cursor;

import com.google.android.gms.location.Geofence;
import com.uni.kevintruong.flextime.models.GeoLocation;
import com.uni.kevintruong.flextime.models.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by kwan8 on 2015-11-30.
 * Converts db data to geolocation and session objects and converts geoLocation to geofence objects
 */
public class Converter
{
    /**
     * Converts Geolocation data from database to geolocations Objects
     * @param geoLocatonDataFromDb
     * @return
     */
    public ArrayList<GeoLocation> convertToGeoLocations(Cursor geoLocatonDataFromDb)
    {
        ArrayList<GeoLocation> geoLocations = new ArrayList<>();

        if (geoLocatonDataFromDb.moveToFirst())
        {
            do
            {
                {
                    GeoLocation geoLocation = new GeoLocation(geoLocatonDataFromDb.getInt(0), geoLocatonDataFromDb.getString(1), geoLocatonDataFromDb.getDouble(2), geoLocatonDataFromDb.getDouble(3), geoLocatonDataFromDb.getInt(4));
                    geoLocations.add(geoLocation);
                }
            } while (geoLocatonDataFromDb.moveToNext());
        }

        return geoLocations;
    }

    /**
     * Converts geoLocations to geofences
     * @param geoLocations we want to convert
     * @return list of geofences
     */
    public ArrayList<Geofence> convertGeolocationsToGeofences(ArrayList<GeoLocation> geoLocations)
    {
        ArrayList<Geofence> temp = new ArrayList<>();

        for (int i = 0; i < geoLocations.size(); i++)
        {
            temp.add(buildGoefence(geoLocations.get(i)));
        }

        return temp;
    }

    /**
     * Converts GeoLocation to geoFence
     *
     * @param geoLocation to be converted
     * @return geoFence
     */
    public Geofence buildGoefence(GeoLocation geoLocation)
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

    /**
     * Converts data from db to sesson objects
     * @param geoSessionsDataFromDb data from db
     * @return list of sessions
     */
    public ArrayList<Session> convertToSessions(Cursor geoSessionsDataFromDb)
    {
        ArrayList<Session> sessions = new ArrayList<>();

        if (geoSessionsDataFromDb.moveToFirst())
        {
            do
            {
                {
                    Calendar cl = new GregorianCalendar();
                    cl.setTimeInMillis(new Long(geoSessionsDataFromDb.getLong(1)));
                    Date date = cl.getTime();
                    cl.setTimeInMillis(new Long(geoSessionsDataFromDb.getLong(2)));
                    Date enter = cl.getTime();
                    cl.setTimeInMillis(new Long(geoSessionsDataFromDb.getLong(3)));
                    Date exit = cl.getTime();

                    Session session = new Session(geoSessionsDataFromDb.getString(0), date, enter, exit, geoSessionsDataFromDb.getLong(4), geoSessionsDataFromDb.getInt(5));
                    sessions.add(session);
                }
            } while (geoSessionsDataFromDb.moveToNext());
        }

        return sessions;
    }
}
