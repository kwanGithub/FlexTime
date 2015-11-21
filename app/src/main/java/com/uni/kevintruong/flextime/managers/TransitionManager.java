package com.uni.kevintruong.flextime.managers;

import com.uni.kevintruong.flextime.models.Session;

import java.util.Date;

/**
 * Created by kwan8 on 2015-11-21.
 */
public class TransitionManager
{

    private static TransitionManager instance;
    private Session currentSession;

    public static synchronized TransitionManager getInstance()
    {
        if (instance == null)
        {
            instance = new TransitionManager();
        }
        return instance;
    }

    public Session startSession(String name, Date enter)
    {
      return this.currentSession = new Session(name, enter);
    }

    public Session endSession(Date exit)
    {
        this.currentSession.setExit(exit);
        return this.currentSession;
    }

}
