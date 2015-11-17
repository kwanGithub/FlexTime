package com.uni.kevintruong.flextime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.models.Session;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created by kwan8 on 2015-11-17.
 */
public class SessionAdapter extends ArrayAdapter<Session>
{
    public SessionAdapter(Context context, Session[] resource)
    {
        super(context, R.layout.session_row, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View locationView = layoutInflater.inflate(R.layout.session_row, parent, false);

        Session singleSession = getItem(position);
        TextView locationDate = (TextView) locationView.findViewById(R.id.locationDate);
        TextView locationDay = (TextView) locationView.findViewById(R.id.locationDay);
        TextView locationEnter = (TextView) locationView.findViewById(R.id.locationEnterTime);
        TextView locationExit = (TextView) locationView.findViewById(R.id.locationExitTime);
        TextView locationDuration = (TextView) locationView.findViewById(R.id.locationDuration);

        SimpleDateFormat sdfTime = new SimpleDateFormat("kk:mm");
        SimpleDateFormat sdfDay = new SimpleDateFormat("dd/MM");
        SimpleDateFormat sdfYear = new SimpleDateFormat("EEE dd MMM. yyyy");

        locationDate.setText(sdfYear.format(singleSession.getEnter()));
        locationDay.setText(sdfDay.format(singleSession.getEnter()));
        locationEnter.setText(sdfTime.format(singleSession.getEnter()));
        locationExit.setText(sdfTime.format(singleSession.getExit()));
        locationDuration.setText(convertMillis(singleSession.getDuration()));

        return locationView;
    }

    private String convertMillis(long miliSeconds)
    {
        int hrs = (int) TimeUnit.MILLISECONDS.toHours(miliSeconds) % 24;
        int min = (int) TimeUnit.MILLISECONDS.toMinutes(miliSeconds) % 60;
        int sec = (int) TimeUnit.MILLISECONDS.toSeconds(miliSeconds) % 60;
        return String.format("%02d:%02d:%02d", hrs, min, sec);
    }
}
