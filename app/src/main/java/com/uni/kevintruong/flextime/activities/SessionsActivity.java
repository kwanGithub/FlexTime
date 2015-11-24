package com.uni.kevintruong.flextime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        ArrayList<Session> sessionsArrayList = this.getIntent().getParcelableArrayListExtra("sessions");

        Session[] sessionsArray = mapListToArray(sessionsArrayList);
        ListAdapter sessionsAdapter = new SessionAdapter(this, sessionsArray);

        ListView sessionsListView = (ListView) findViewById(R.id.sessionsListView);

        View headerView = View.inflate(this, R.layout.session_row, null);

        sessionsListView.addHeaderView(headerView);
        sessionsListView.setAdapter(sessionsAdapter);


        Button singleBtn = (Button) findViewById(R.id.single);
        singleBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), "Single clicked", Toast.LENGTH_SHORT).show();
            }
        });
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
