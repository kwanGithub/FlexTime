package com.uni.kevintruong.flextime.managers;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.uni.kevintruong.flextime.helpers.Converter;
import com.uni.kevintruong.flextime.models.Session;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by kwan8 on 2015-11-30.
 * Get and sets session objects from teh database. Adds sessionbtn listener
 */
public class SessionManager
{
    private Converter _converter;
    private DatabaseManager _db;

    public SessionManager(Context context)
    {
        _converter = new Converter();
        _db = DatabaseManager.getInstance(context);
    }

    public ArrayList<Session> getSessions(int geoLocationId) throws ParseException
    {
        return _converter.convertToSessions(_db.getSessions(geoLocationId));
    }

    public void addSession(Session session)
    {
        _db.addSessionDb(session);
    }

    public View.OnClickListener sessionsBtnListener(final Context context, final String activity, final ArrayList<Session> sessions)
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(activity);
                intent.putParcelableArrayListExtra("sessions", sessions);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };

        return listener;
    }
}
