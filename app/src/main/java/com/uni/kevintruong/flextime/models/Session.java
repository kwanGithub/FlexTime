package com.uni.kevintruong.flextime.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kwan8 on 2015-11-02.
 */
public class Session implements Parcelable
{
    private String geofenceId;
    private Date sessionStart;
    private Date sessionEnd;
    private long duration;

    public Session(String geofenceId, Date start)
    {
        this.geofenceId = geofenceId;
        this.sessionStart = start;
    }

    private Session(Parcel parcel)
    {
        this.geofenceId = parcel.readString();
        this.sessionStart = new Date(parcel.readLong());
        this.sessionEnd = new Date(parcel.readLong());
        this.duration = parcel.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(geofenceId);
        dest.writeLong(this.sessionStart.getTime());
        dest.writeLong(this.sessionEnd.getTime());
        dest.writeLong(duration);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public String getGeofenceId()
    {
        return geofenceId;
    }

    public void setGeofenceId(String geofenceId)
    {
        this.geofenceId = geofenceId;
    }

    public Date getSessionStart()
    {
        return sessionStart;
    }

    public Date getSessionEnd()
    {
        return sessionEnd;
    }

    public void setSessionEnd(Date sessionEnd)
    {
        Calendar calendar = Calendar.getInstance();

        this.sessionEnd = sessionEnd;
        calendar.setTime(this.sessionStart);
        long sessinoStartMillis = calendar.getTimeInMillis();

        calendar.setTime(this.sessionEnd);
        long sessionEndMillis = calendar.getTimeInMillis();

        this.duration = (sessionEndMillis - sessinoStartMillis);
    }

    public long getDuration()
    {
        return duration;
    }

    public void setDuration(long duration)
    {
        this.duration = duration;
    }

    public static final Creator<Session> CREATOR = new Creator<Session>()
    {
        @Override
        public Session createFromParcel(Parcel in)
        {
            return new Session(in);
        }

        @Override
        public Session[] newArray(int size)
        {
            return new Session[size];
        }
    };
}
