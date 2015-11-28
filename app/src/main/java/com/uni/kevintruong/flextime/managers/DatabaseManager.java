package com.uni.kevintruong.flextime.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.android.gms.location.Geofence;
import com.uni.kevintruong.flextime.models.GeoLocation;
import com.uni.kevintruong.flextime.models.Session;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by kwan8 on 2015-11-02.
 * Retrieves, sets and Converts Data to and from Sqlite database
 */
public class DatabaseManager extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "flexTime.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseManager instance;
    //DEBUG
    //private ArrayList<GeoLocation> geoLocations;

    public static synchronized DatabaseManager getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new DatabaseManager(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //this.geoLocations = new ArrayList<>();
        //DEBUG TEST Locations
        //this.geoLocations = getGeolocationTestData();
    }

    /**
     * Creates database tables
     * @param db the db we are creating the table for
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTablePositions = "CREATE TABLE geolocations (id INTEGER, name TEXT, latitude REAL , longitude REAL, radius INTEGER, PRIMARY KEY (id))";
        db.execSQL(createTablePositions);

        String CreateTableSessions = "CREATE TABLE sessions (session_id TEXT, date REAL, enter REAL, exit REAL, duration REAL, geolocationId_fk INTEGER, FOREIGN KEY(geolocationId_fk) REFERENCES geolocations(id), PRIMARY KEY (session_id, date, enter))";
        db.execSQL(CreateTableSessions);
    }

    /**
     * If table entities are changed or updated with new ones we need to drop the old one
     * @param db the db we are updating
     * @param oldVersion db version
     * @param newVersion db version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String query = "DROP TABLE IF EXISTS geolocations";
        db.execSQL(query);
        String query2 = "DROP TABLE IF EXISTS sessions";
        db.execSQL(query2);

        onCreate(db);
    }

    /**
     * Inputs geoLoaction data to database
     * @param geoLocation
     */
    public void addGeoLocation(GeoLocation geoLocation)
    {
        //Debug
        //deleteGeoLocation(1);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", geoLocation.getId());
        values.put("name", geoLocation.getName());
        values.put("latitude", geoLocation.getCoordinates().latitude);
        values.put("longitude", geoLocation.getCoordinates().longitude);
        values.put("radius", geoLocation.getRadius());

        db.insert("geolocations", null, values);
        db.close();
    }

    /**
     * Deletes geoLocation by its Id
     * @param geoLocationId
     */
    public void deleteGeoLocation(int geoLocationId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM geolocations WHERE id =" + geoLocationId + ";");
    }

    /**
     * Inserts session to the session table
     * @param session the session we want to add
     */
    public void addSession(Session session)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Calendar cl = new GregorianCalendar();

        values.put("session_id", session.getGeofenceId());
        cl.setTime(session.getDate());
        values.put("date", cl.getTimeInMillis());
        cl.setTime(session.getEnter());
        values.put("enter", cl.getTimeInMillis());
        cl.setTime(session.getExit());
        values.put("exit", cl.getTimeInMillis());
        values.put("duration", session.getDuration());
        values.put("geolocationId_fk", session.getGeoLocationId_fk());

        db.insert("sessions", null, values);
        db.close();
    }

    // Wont be used for this version maybe later?
    public void deleteSession(int sessionId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM sessions WHERE geolocationId_fk =" + sessionId + ";");
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

    /**
     * Gets a GeoLocation from db by its name
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

    /**
     * Converts GeoLocation to geoFence
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
     * Gets all sessions by geoLocationId
     * @param geoLocationId of the sessions
     * @return List of sessions
     * @throws ParseException
     */
    public ArrayList<Session> getSessions(int geoLocationId) throws ParseException
    {
        //Debug
        //deleteSession(1);

        ArrayList<Session> sessions = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String getLocationsQuery = "SELECT * FROM sessions WHERE geolocationId_fk = " + geoLocationId;

        Cursor cursor = db.rawQuery(getLocationsQuery, null);

        if (cursor.moveToFirst())
        {
            do
            {
                {
                    Calendar cl = new GregorianCalendar();
                    cl.setTimeInMillis(new Long(cursor.getLong(1)));
                    Date date = cl.getTime();
                    cl.setTimeInMillis(new Long(cursor.getLong(2)));
                    Date enter = cl.getTime();
                    cl.setTimeInMillis(new Long(cursor.getLong(3)));
                    Date exit = cl.getTime();

                    Session session = new Session(cursor.getString(0), date, enter, exit, cursor.getLong(4), cursor.getInt(5));
                    sessions.add(session);
                }
            } while (cursor.moveToNext());
        }

        return sessions;
    }

    /**
     * Get a list of all the GeoLocations in the table
     * @return list of geoLocations
     */
    public ArrayList<GeoLocation> getGeoLocations()
    {
        //Debug
        //deleteGeoLocation(1);

        ArrayList<GeoLocation> geoLocations = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String getLocationsQuery = "SELECT * FROM geolocations";

        Cursor cursor = db.rawQuery(getLocationsQuery, null);

        if (cursor.moveToFirst())
        {
            do
            {
                {
                    GeoLocation place = new GeoLocation(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getInt(4));
                    geoLocations.add(place);
                }
            } while (cursor.moveToNext());
        }

        return geoLocations;
    }

    //DEBUG
    public ArrayList<GeoLocation> getGeolocationTestData()
    {
        ArrayList<GeoLocation> temp = new ArrayList<>();
        temp.add(new GeoLocation(1, "Hjällbo", 57.771702, 12.033851, 100));
        temp.add(new GeoLocation(2, "Eriksbo", 57.771462, 12.026233, 100));
        temp.add(new GeoLocation(3, "Göteborg", 57.769425, 12.025793, 100));
        temp.add(new GeoLocation(4, "Sthlm", 57.770684, 12.020762, 100));
        temp.add(new GeoLocation(5, "Hemma", 57.769567, 12.030206, 100));

        return temp;
    }

    //DEBUG
    public ArrayList<Session> getSessionTestData()
    {
        Calendar cl = new GregorianCalendar();
        Session session = new Session("Hemma", cl.getTime(), 1);

        cl.add(Calendar.HOUR_OF_DAY, 1);
        cl.add(Calendar.MINUTE, 23);
        cl.add(Calendar.SECOND, 33);

        session.setExit(cl.getTime());
        ArrayList<Session> temp = new ArrayList<>();
        temp.add(session);
        temp.add(session);

        return temp;
    }

    //DEBUG return entered to verify geoLocation is created?
    public String databaseToString()
    {
        String dbString = "";
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM geolocations WHERE id = 1";

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        dbString += c.getString(c.getColumnIndex("id"));
        dbString += "\n";
        dbString += c.getString(c.getColumnIndex("name"));
        dbString += "\n";
        dbString += c.getString(c.getColumnIndex("latitude"));
        dbString += "\n";
        dbString += c.getString(c.getColumnIndex("longitude"));
        dbString += "\n";
        dbString += c.getString(c.getColumnIndex("radius"));
        dbString += "\n";

        db.close();
        return dbString;
    }

    //DEBUG return entered to verify session is created?
    public String databaseSessionsToString()
    {
        String dbString = "";
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM sessions WHERE geolocationId_fk = 1";

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        dbString += c.getString(c.getColumnIndex("session_id"));
        dbString += "\n";
        dbString += c.getString(c.getColumnIndex("date"));
        dbString += "\n";
        dbString += c.getString(c.getColumnIndex("enter"));
        dbString += "\n";
        dbString += c.getString(c.getColumnIndex("exit"));
        dbString += "\n";
        dbString += c.getString(c.getColumnIndex("duration"));
        dbString += "\n";
        dbString += c.getString(c.getColumnIndex("geolocationId_fk"));
        dbString += "\n";

        db.close();
        return dbString;
    }

}
