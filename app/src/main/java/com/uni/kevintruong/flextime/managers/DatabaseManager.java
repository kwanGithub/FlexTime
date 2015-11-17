package com.uni.kevintruong.flextime.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.location.Geofence;
import com.uni.kevintruong.flextime.models.GeoLocation;

import java.util.ArrayList;

/**
 * Created by kwan8 on 2015-11-02.
 */
public class DatabaseManager extends SQLiteOpenHelper
{
    private static DatabaseManager instance;
    private ArrayList<GeoLocation> geoLocations;

    public static synchronized DatabaseManager getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new DatabaseManager(context.getApplicationContext());
        }
        return instance;
    }

    public ArrayList<GeoLocation> getGeoLocations()
    {
        return this.geoLocations;
    }

    public void addGeoLocations(int id, String name, double latitude, double longitude)
    {
        int radius = 100;
        this.geoLocations.add(new GeoLocation(id, name, latitude, longitude, radius));
    }

    private DatabaseManager(Context context)
    {
        super(context, "flexTime.db", null, 1);
        this.geoLocations = getGeolocationTestData();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTablePositions = "CREATE TABLE geolocations (id INTEGER PRIMARY KEY, name TEXT, latitude REAL , longitude REAL, radius INTEGER)";
        db.execSQL(createTablePositions);

        String CreateTableSessions = "CREATE TABLE sessions (session_id INTEGER  PRIMARY KEY, enter INTEGER," +
                "exit INTEGER, duration INTEGER, positionId_fk INTEGER, FOREIGN KEY(id_fk) REFERENCES geolocations(id))";


        db.execSQL(CreateTableSessions);
    }

    public ArrayList<Geofence> mapGeolocationsToGeofences(ArrayList<GeoLocation> geoLocations)
    {
        ArrayList<Geofence> temp = new ArrayList<>();

        for (int i = 0; i < geoLocations.size(); i++)
        {
            temp.add(buildGoefence(geoLocations.get(i)));
        }

        return temp;
    }

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

    public ArrayList<GeoLocation> getGeolocationTestData()
    {
        ArrayList<GeoLocation> temp = new ArrayList<>();
        temp.add(new GeoLocation(1, "test1", 57.771702, 12.033851, 100));
        temp.add(new GeoLocation(2, "test2", 57.771462, 12.026233, 100));
        temp.add(new GeoLocation(3, "test3", 57.769425, 12.025793, 100));
        temp.add(new GeoLocation(4, "test4", 57.770684, 12.020762, 100));
        temp.add(new GeoLocation(5, "HEMMA", 57.769567, 12.030206, 100));

        return temp;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String query = "DROP TABLE IF EXISTS geolocations";
        db.execSQL(query);
        String query2 = "DROP TABLE IF EXISTS sessions";
        db.execSQL(query2);

        onCreate(db);
    }
}
