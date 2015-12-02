package com.uni.kevintruong.flextime.managers;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.uni.kevintruong.flextime.models.Session;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by kwan8 on 2015-12-02.
 */
public class TransitionManagerTest extends AndroidTestCase
{

    Calendar cl;
    TransitionManager tm;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        cl = new GregorianCalendar();
        tm = TransitionManager.getInstance();
    }

    @SmallTest
    public void testStartSession()
    {
        Date enter = cl.getTime();
        Session response = tm.startSession("StartSessionTest", enter, 1);

        assertEquals(1, response.getGeoLocationId_fk());
        assertEquals("StartSessionTest", response.getGeofenceId());
        assertEquals(enter, response.getEnter());
    }

    @SmallTest
    public void testEndSession()
    {
        Date enter = cl.getTime();
        Date end = cl.getTime();

        Session startSession = tm.startSession("StartSessionTest", enter, 2);

        Session response = tm.endSession(end);

        assertEquals(2, response.getGeoLocationId_fk());
        assertEquals("StartSessionTest", response.getGeofenceId());
        assertEquals(enter, response.getEnter());
        assertEquals(enter, response.getDate());
        assertEquals(end, response.getExit());
        assertNotNull(response.getDuration());
    }

    @SmallTest
    public void testGetCurrentSession()
    {
        Date enter = cl.getTime();
        Date end = cl.getTime();

        Session startSession = tm.startSession("GetCurrentSessionTest", enter, 3);

        Session endSession = tm.endSession(end);

        Session response = tm.getCurrentSession();

        assertEquals(3, response.getGeoLocationId_fk());
        assertEquals("GetCurrentSessionTest", response.getGeofenceId());
        assertEquals(enter, response.getEnter());
        assertEquals(enter, response.getDate());
        assertEquals(end, response.getExit());
        assertNotNull(response.getDuration());

    }

    @SmallTest
    public void testSetCurrentSession()
    {
        Date enter = cl.getTime();
        Date end = cl.getTime();

        Session startSession = tm.startSession("SessionToBeSwitched", enter, 1);
        Session endSession = tm.endSession(end);

        Session responseToBeReplaced = tm.getCurrentSession();

        assertEquals(1, endSession.getGeoLocationId_fk());
        assertEquals("SessionToBeSwitched", responseToBeReplaced.getGeofenceId());
        assertEquals(enter, responseToBeReplaced.getEnter());
        assertEquals(enter, responseToBeReplaced.getDate());
        assertEquals(end, responseToBeReplaced.getExit());
        assertNotNull(responseToBeReplaced.getDuration());

        Session setNewSession = new Session("SetSesstionTest", enter, enter, end, 123, 4);
        tm.setCurrentSession(setNewSession);

        Session response = tm.getCurrentSession();

        assertEquals(4, response.getGeoLocationId_fk());
        assertEquals("SetSesstionTest", response.getGeofenceId());
        assertEquals(enter, response.getEnter());
        assertEquals(enter, response.getDate());
        assertEquals(end, response.getExit());
        assertEquals(123, response.getDuration());
    }
}
