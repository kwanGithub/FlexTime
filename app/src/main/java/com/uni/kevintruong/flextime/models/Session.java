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
    private Date date;
    private Date enter;
    private Date exit;
    private long duration;

    public Session(String geofenceId, Date start)
    {
        this.geofenceId = geofenceId;
        this.date = start;
        this.enter = start;
    }

    private Session(Parcel parcel)
    {
        this.geofenceId = parcel.readString();
        this.date =  new Date(parcel.readLong());
        this.enter = new Date(parcel.readLong());
        this.exit = new Date(parcel.readLong());
        this.duration = parcel.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.geofenceId);
        dest.writeLong(this.date.getTime());
        dest.writeLong(this.enter.getTime());
        dest.writeLong(this.exit.getTime());
        dest.writeLong(this.duration);
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

    public Date getEnter()
    {
        return enter;
    }

    public Date getExit()
    {
        return exit;
    }

    public void setExit(Date exit)
    {
        Calendar calendar = Calendar.getInstance();

        this.exit = exit;
        calendar.setTime(this.enter);
        long sessinoStartMillis = calendar.getTimeInMillis();

        calendar.setTime(this.exit);
        long exitMillis = calendar.getTimeInMillis();

        this.duration = (exitMillis - sessinoStartMillis);
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
