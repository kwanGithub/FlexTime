package com.uni.kevintruong.flextime.models;

/**
 * Created by kwan8 on 2015-11-03.
 */
public class Position
{
    private int _id;
    private String _name;
    private double _latitude;
    private double _longitude;
    private int _radius;

    public Position(int _id, String _name, double _latitude, double _longitude, int _radius)
    {
        this._id = _id;
        this._name = _name;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._radius = _radius;
    }

    public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }
    public String get_name()
    {
        return _name;
    }

    public void set_name(String _name)
    {
        this._name = _name;
    }

    public double get_latitude()
    {
        return _latitude;
    }

    public void set_latitude(double _latitude)
    {
        this._latitude = _latitude;
    }

    public double get_longitude()
    {
        return _longitude;
    }

    public void set_longitude(double _longitude)
    {
        this._longitude = _longitude;
    }

    public int get_radius()
    {
        return _radius;
    }

    public void set_radius(int _radius)
    {
        this._radius = _radius;
    }


}
