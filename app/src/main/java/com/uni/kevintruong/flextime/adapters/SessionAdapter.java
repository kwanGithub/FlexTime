package com.uni.kevintruong.flextime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.models.Session;

/**
 * Created by kwan8 on 2015-11-16.
 */
class SessionAdapter extends ArrayAdapter<Session>
{
    public SessionAdapter(Context context, Session[] resource)
    {
        super(context, R.layout.location_row, resource);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View locationView = layoutInflater.inflate(R.layout.location_row, parent, false);

        Session singleSession = getItem(position);
        TextView locationDate = (TextView) locationView.findViewById(R.id.locationDate);
        TextView locationDay = (TextView) locationView.findViewById(R.id.locationDay);
        TextView locationEnter = (TextView) locationView.findViewById(R.id.locationEnterTime);
        TextView locationExit = (TextView) locationView.findViewById(R.id.locationExitTime);
        TextView locationDuration = (TextView) locationView.findViewById(R.id.locationDuration);


        return locationView;
    }
}
