package com.uni.kevintruong.flextime.managers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.uni.kevintruong.flextime.models.Session;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kwan8 on 2015-11-21.
 * Handles the creation of Sessions and adds buttonListner for the session stats buttons
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
      return _currentSession = new Session(name, enter, geoLocationId);
    }

    public Session endSession(Date exit)
    {
        _currentSession.setExit(exit);
        return _currentSession ;
    }

    public View.OnClickListener sessionsBtnListener(final Context context, final String activity, final ArrayList<Session> sessions, final String toastMessage)
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context.getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity);
                intent.putParcelableArrayListExtra("sessions", sessions);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };


        return listener;
    }

}
