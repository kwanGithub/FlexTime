package com.uni.kevintruong.flextime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.adapters.SessionAdapter;
import com.uni.kevintruong.flextime.models.Session;

public class SessionsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        Bundle bundle = this.getIntent().getExtras();
        Session session = bundle.getParcelable("Session");
        Session[] sessions = {session};
        ListAdapter sessionsAdapter = new SessionAdapter(this, sessions);

        ListView sessionsListView = (ListView) findViewById(R.id.sessionsListView);
        sessionsListView.setAdapter(sessionsAdapter);
    }
}
