package com.uni.kevintruong.flextime.managers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.uni.kevintruong.flextime.models.Session;

import java.util.Date;

/**
 * Created by kwan8 on 2015-11-21.
 */
public class SessionManager
{

    private static SessionManager instance;
    private Session currentSession;

    public static synchronized SessionManager getInstance()
    {
        if (instance == null)
        {
            instance = new SessionManager();
        }
        return instance;
    }

    public Session startSession(String name, Date enter, int geoLocationId)
    {
      return this.currentSession = new Session(name, enter, geoLocationId);
    }

    public Session endSession(Date exit)
    {
        this.currentSession.setExit(exit);
        return this.currentSession;
    }

    public View.OnClickListener sessionsBtnListener(final Context context, final String activity, final String toastMessage)
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context.getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };

        return listener;
    }

}
