package com.uni.kevintruong.flextime.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

/**
 * Created by kwan8 on 2015-11-03.
 */
public class GeoLocation implements Parcelable
{
    private int id;
    private String name;
    private LatLng coordinates;
    private double latitude;
    private double longitude;
    private int radius;
    private ArrayList<Session> sessions;

    public GeoLocation(int id, String name, double latitude, double longitude, int radius)
    {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.coordinates = new LatLng(this.latitude, this.longitude);
        this.sessions = new ArrayList<>();
    }

    //Enables the class to be passed into intents
    private GeoLocation(Parcel parcel)
    {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        this.latitude = parcel.readDouble();
        this.longitude = parcel.readDouble();
        this.radius = parcel.readInt();
        this.coordinates = new LatLng(this.latitude, this.longitude);
        this.sessions = parcel.readArrayList(null);
    }

    public void setSessions(ArrayList<Session> sessions)
    {
        this.sessions = sessions;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeInt(radius);
        dest.writeList(sessions);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public ArrayList<Session> getSessions()
    {
        return this.sessions;
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

      public int getRadius()
    {
        return this.radius;
    }

    public static final Creator<GeoLocation> CREATOR = new Creator<GeoLocation>(){

        @Override
        public GeoLocation createFromParcel(Parcel source)
        {
            return new GeoLocation(source);
        }

        @Override
        public GeoLocation[] newArray(int size)
        {
            return new GeoLocation[size];
        }
    };
}
