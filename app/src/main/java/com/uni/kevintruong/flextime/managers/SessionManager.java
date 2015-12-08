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

    /**
     * Get sessions from db and converts them to session objects
     * @param geoLocationId
     * @return list of sessions
     * @throws ParseException
     */
    public ArrayList<Session> getSessions(int geoLocationId) throws ParseException
    {
        return _converter.convertToSessions(_db.getSessions(geoLocationId));
    }

    /**
     * Add sessions to database
     * @param session we want to add
     */
    public void addSession(Session session)
    {
        _db.addSessionDb(session);
    }

    /**
     * Creates a onclicklister for sessionbtns, and starts an activity
     * @param context the context that wants the listener
     * @param activity the activity we changening to
     * @param sessions list of session for the next activity
     * @return onclicklistener
     */
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
