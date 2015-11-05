package com.uni.kevintruong.flextime.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kwan8 on 2015-11-02.
 */
public class DatabaseManager extends SQLiteOpenHelper
{
    public DatabaseManager(Context context)
    {
        super(context, "places.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTablePlace = "CREATE TABLE position (id INTEGER PRIMARY KEY, name TEXT, latitude REAL , longitude REAL, radius INTEGER)";
        db.execSQL(createTablePlace);

        String CreateTablePlaceData = "CREATE TABLE session (session_date NUMERIC  PRIMARY KEY, enter INTEGER," +
                "exit INTEGER, duration INTEGER, positionId_fk INTEGER, FOREIGN KEY(id_fk) REFERENCES place(id))";


        db.execSQL(CreateTablePlaceData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String query = "DROP TABLE IF EXISTS place";
        db.execSQL(query);
        String query2 = "DROP TABLE IF EXISTS session";
        db.execSQL(query2);

        onCreate(db);
    }
}
