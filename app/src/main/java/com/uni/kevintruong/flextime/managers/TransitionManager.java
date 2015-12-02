package com.uni.kevintruong.flextime.managers;

import com.uni.kevintruong.flextime.models.Session;

import java.util.Date;

/**
 * Created by kwan8 on 2015-11-21.
 * Handles the creation of Sessions when a Transition has is activated by entering and leaving a geofence
 */
public class TransitionManager
{
    private static TransitionManager instance;
    private Session _currentSession;

    public static synchronized TransitionManager getInstance()
    {
        if (instance == null)
        {
            instance = new TransitionManager();
        }
        return instance;
    }

    public Session startSession(String name, Date enter, int geoLocationId)
    {
        _currentSession = new Session(name, enter, geoLocationId);
        return _currentSession;
    }

    public Session endSession(Date exit)
    {
        _currentSession.setExit(exit);
        return _currentSession;
    }

    public Session getCurrentSession()
    {
        return _currentSession;
    }

    public void setCurrentSession(Session currentSession)
    {
        _currentSession = currentSession;
    }
}
