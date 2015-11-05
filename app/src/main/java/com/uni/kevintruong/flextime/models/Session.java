package com.uni.kevintruong.flextime.models;

import java.util.Date;

/**
 * Created by kwan8 on 2015-11-02.
 */
public class Session
{
    private int _positionId;
    private Date _start;
    private Date _end;
    private long _duration;

    public Session(int _positionId, Date _end, Date _start)
    {
        this._positionId = _positionId;
        this._end = _end;
        this._start = _start;
    }

    public Date get_start()
    {
        return _start;
    }

    public void set_start(Date _start)
    {
        this._start = _start;
    }

    public Date get_end()
    {
        return _end;
    }

    public void set_end(Date _end)
    {
        this._end = _end;
    }

    public long get_duration()
    {
        return _duration;
    }

    public void set_duration(long _duration)
    {
        this._duration = _duration;
    }

    public int get_positionId()
    {
        return _positionId;
    }

    public void set_positionId(int _positionId)
    {
        this._positionId = _positionId;
    }

}
