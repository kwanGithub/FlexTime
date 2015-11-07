package com.uni.kevintruong.flextime.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kwan8 on 2015-11-03.
 */
public class GeoLocation
{
    private int id;
    private String name;
    private LatLng coordinates;
        private int radius;

    public GeoLocation(int id, String name, double latitude, double longitude, int radius)
    {
        this.id = id;
        this.name = name;
        this.coordinates = new LatLng(latitude, longitude);
        this.radius = radius;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LatLng getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates)
    {
        this.coordinates = coordinates;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }
}
