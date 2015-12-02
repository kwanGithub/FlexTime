package com.uni.kevintruong.flextime.managers;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.SmallTest;

import com.uni.kevintruong.flextime.helpers.Converter;
import com.uni.kevintruong.flextime.models.GeoLocation;
import com.uni.kevintruong.flextime.models.Session;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by kwan8 on 2015-12-02.
 */
public class DatabaseManagerTest  extends AndroidTestCase
{
    DatabaseManager databaseManager;
    Converter converter;
    int geolocationToDelete;
    int sessionToDelete;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        databaseManager = DatabaseManager.getInstance(context);
        converter = new Converter();

    }

    @SmallTest
    public void testAddGeoLocationToDb()
    {
        geolocationToDelete = 1;
        GeoLocation geoLocation = new GeoLocation(geolocationToDelete, "addGeoLoactionToDbTest", 57.1234, 12.1234, 20);


        databaseManager.addGeoLocationDb(geoLocation);

       GeoLocation response = converter.convertToGeoLocations(databaseManager.getGeoLocationsDb()).get(0);

        assertEquals(1, response.getId());
        assertEquals("addGeoLoactionToDbTest", response.getName());
        assertEquals(57.1234, response.getCoordinates().latitude);
        assertEquals(12.1234, response.getCoordinates().longitude);
        assertEquals(20, response.getRadius());
    }

    @SmallTest
    public void testAddSessionToDb() throws ParseException
    {
        geolocationToDelete = 1;
        Calendar cl = new GregorianCalendar();
        Date date = cl.getTime();
        Date enter = cl.getTime();
        Date exit = cl.getTime();

        Session session = new Session("SessionDbTest", date, enter, exit, 33123, geolocationToDelete);


        databaseManager.addSessionDb(session);

        Session response = converter.convertToSessions(databaseManager.getSessions(geolocationToDelete)).get(0);

        assertEquals(1, response.getGeoLocationId_fk());
        assertEquals("SessionDbTest", response.getGeofenceId());
        assertEquals(date, response.getDate());
        assertEquals(enter, response.getEnter());
        assertEquals(exit, response.getExit());
        assertEquals(33123, response.getDuration());
    }

    @Override
    protected void tearDown()
    {
        databaseManager.deleteGeoLocationDb(geolocationToDelete);
    }
}
