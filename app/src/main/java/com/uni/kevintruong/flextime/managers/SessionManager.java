package com.uni.kevintruong.flextime.managers;
import android.content.Context;
import com.uni.kevintruong.flextime.helpers.Converter;
import com.uni.kevintruong.flextime.models.Session;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by kwan8 on 2015-11-30.
 */
public class SessionManager
{
    private Converter _converter;
    private DatabaseManager _db;

    public SessionManager(Context context)
    {
        this._converter = new Converter();
        this._db = DatabaseManager.getInstance(context);
    }

    public ArrayList<Session> getSessions(int geoLocationId) throws ParseException
    {
        return _converter.convertToSessions(_db.getSessions(geoLocationId));
    }

    public void addSession(Session session)
    {
        _db.addSessionDb(session);
    }
}
