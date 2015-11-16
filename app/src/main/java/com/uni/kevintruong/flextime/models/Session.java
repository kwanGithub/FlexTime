package com.uni.kevintruong.flextime.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by kwan8 on 2015-11-02.
 */
public class Session implements Parcelable
{
    private int positionId;
    private Date sessionStart;
    private Date sessionEnd;
    private long duration;

    public Session(int positionId, Date start)
    {
        this.positionId = positionId;
        this.sessionStart = start;
    }

    private Session(Parcel parcel)
    {
        this.positionId = parcel.readInt();
        this.sessionStart = new Date(parcel.readLong());
        this.sessionEnd = new Date(parcel.readLong());
        this.duration = parcel.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(positionId);
        dest.writeLong(this.sessionStart.getTime());
        dest.writeLong(this.sessionEnd.getTime());
        dest.writeLong(duration);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public int getPositionId()
    {
        return positionId;
    }

    public void setPositionId(int positionId)
    {
        this.positionId = positionId;
    }

    public Date getSessionStart()
    {
        return sessionStart;
    }

    public void setSessionStart(Date sessionStart)
    {
        this.sessionStart = sessionStart;
    }

    public Date getSessionEnd()
    {
        return sessionEnd;
    }

    public void setSessionEnd(Date sessionEnd)
    {
        this.sessionEnd = sessionEnd;
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
