package com.uni.kevintruong.flextime.models;

import java.util.Date;

/**
 * Created by kwan8 on 2015-11-02.
 */
public class Session
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
}
