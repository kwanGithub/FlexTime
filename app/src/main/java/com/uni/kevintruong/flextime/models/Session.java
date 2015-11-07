package com.uni.kevintruong.flextime.models;

import java.util.Date;

/**
 * Created by kwan8 on 2015-11-02.
 */
public class Session
{
    private int _positionId;
    private Date _sessionStart;
    private Date _sessionEnd;
    private long _duration;

    public Session(int positionId, Date start)
    {
        _positionId = positionId;
        _sessionStart = start;
    }

    public Date getSessionStart()
    {
        return _sessionStart;
    }

    public void setSessionStart(Date _start)
    {
        _sessionStart = _start;
    }

    public Date get_sessionEnd()
    {
        return _sessionEnd;
    }

    public void set_sessionEnd(Date _end)
    {
        _sessionEnd = _end;
    }

    public long getDuration()
    {
      return _duration;
    }

    public void setDuration(long _duration)
    {
        _duration = _duration;
    }

    public int getPositionId()
    {
       return _positionId;
    }

    public void setPositionId(int _positionId)
    {
        _positionId = _positionId;
    }

}
