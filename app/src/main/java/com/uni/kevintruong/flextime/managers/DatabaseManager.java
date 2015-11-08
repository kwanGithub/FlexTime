package com.uni.kevintruong.flextime.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.uni.kevintruong.flextime.models.GeoLocation;

import java.util.ArrayList;

/**
 * Created by kwan8 on 2015-11-02.
 */
public class DatabaseManager extends SQLiteOpenHelper
{
    private static DatabaseManager instance;

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
        super(context, "flexTime.db", null, 1);
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


    public ArrayList<GeoLocation> getGeolocationTestData()
    {
        ArrayList<GeoLocation> temp = new ArrayList<>();
        temp.add(new GeoLocation(1, "test1", 57.771702, 12.033851, 100));
        temp.add(new GeoLocation(2, "test2", 57.771462, 12.026233, 100));
        temp.add(new GeoLocation(3, "test3", 57.769425, 12.025793, 100));
        temp.add(new GeoLocation(4, "test4", 57.770684, 12.020762, 100));

        return temp;
    }

    public String[] getMyLocationsTestData()
    {
        String[] locations = {"Jobbet",
                "Hemmet",
                "Test",
                "Mölndal"
        };

        return locations;
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
