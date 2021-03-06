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
 * Handles and Converts Sessions data to sessionsRow layout
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
        View sessionView = layoutInflater.inflate(R.layout.session_row, parent, false);

        Session singleSession = getItem(position);

        TextView sessionDay = (TextView) sessionView.findViewById(R.id.sessionDay);
        TextView sessionEnter = (TextView) sessionView.findViewById(R.id.sessionEnter);
        TextView sessionExit = (TextView) sessionView.findViewById(R.id.sessionExit);
        TextView sessionDuration = (TextView) sessionView.findViewById(R.id.sessionDuration);

        //Format for viewing time and date
        SimpleDateFormat sdfTime = new SimpleDateFormat("kk:mm");
        SimpleDateFormat sdfYear = new SimpleDateFormat("EEE dd/MM yyyy");

        sessionDay.setText(sdfYear.format(singleSession.getEnter()));
        sessionEnter.setText(sdfTime.format(singleSession.getEnter()));
        sessionExit.setText(sdfTime.format(singleSession.getExit()));
        sessionDuration.setText(convertMillis(singleSession.getDuration()));

        return sessionView;
    }

    /**
     * Converts milliseconds to hrs, min and sec
     * @param milliSeconds we want to convert
     * @return formated milliseconds to hrs, min and sec
     */
    private String convertMillis(long milliSeconds)
    {
        int hrs = (int) TimeUnit.MILLISECONDS.toHours(milliSeconds) % 24;
        int min = (int) TimeUnit.MILLISECONDS.toMinutes(milliSeconds) % 60;
        int sec = (int) TimeUnit.MILLISECONDS.toSeconds(milliSeconds) % 60;
        return String.format("%02d:%02d:%02d", hrs, min, sec);
    }
}
