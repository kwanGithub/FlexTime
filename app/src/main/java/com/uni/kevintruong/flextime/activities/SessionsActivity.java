package com.uni.kevintruong.flextime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.adapters.SessionAdapter;
import com.uni.kevintruong.flextime.models.Session;

import java.util.ArrayList;

public class SessionsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        Bundle bundle = this.getIntent().getExtras();


        ArrayList<Session> sessionsArrayList = this.getIntent().getParcelableArrayListExtra("test");

        Session[] sessionsArray = mapListToArray(sessionsArrayList);
        ListAdapter sessionsAdapter = new SessionAdapter(this, sessionsArray);

        ListView sessionsListView = (ListView) findViewById(R.id.sessionsListView);
        sessionsListView.setAdapter(sessionsAdapter);
    }

    private Session[] mapListToArray(ArrayList<Session> sessions)
    {
        Session[] temp = new Session[sessions.size()];

        for (int i = 0; i < sessions.size(); i++)
        {
            temp[i] = sessions.get(i);
        }

        return temp;
    }
}
