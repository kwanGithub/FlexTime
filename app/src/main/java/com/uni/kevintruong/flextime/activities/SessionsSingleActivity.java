package com.uni.kevintruong.flextime.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.adapters.SessionAdapter;
import com.uni.kevintruong.flextime.managers.SessionManager;
import com.uni.kevintruong.flextime.models.Session;

import java.util.ArrayList;

public class SessionsSingleActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions_single);

        //Gets sessions of the clicked GeoLocation
        ArrayList<Session> sessionsArrayList = this.getIntent().getParcelableArrayListExtra("sessions");

        Session[] sessionsArray = sessionsArrayList.toArray(new Session[sessionsArrayList.size()]);
        //Sets the sessionList with data
        ListAdapter sessionsAdapter = new SessionAdapter(this, sessionsArray);

        ListView sessionsListView = (ListView) findViewById(R.id.sessionsListView);
        View headerView = View.inflate(this, R.layout.session_row, null);
        //Adds a header to the listView
        sessionsListView.addHeaderView(headerView);
        sessionsListView.setAdapter(sessionsAdapter);

        LinearLayout includedLayout = (LinearLayout) findViewById(R.id.includedSessionsMenuBtns);

        //TODO: Add highlighting to the current Activity
        Button singleBtn = (Button) includedLayout.findViewById(R.id.sessionSingle);
        singleBtn.setBackgroundColor(Color.parseColor("#33b5e5"));
        singleBtn.setTextColor(Color.parseColor("#212121"));
        Button daysBtn = (Button) includedLayout.findViewById(R.id.sessionDay);
        Button weeksBtn = (Button) includedLayout.findViewById(R.id.sessionWeeks);
        Button monthsBtn = (Button) includedLayout.findViewById(R.id.sessionMonths);

        SessionManager sm = new SessionManager(getApplicationContext());

        daysBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsDaysActivity", sessionsArrayList));
        weeksBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsWeeksActivity", sessionsArrayList));
        monthsBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsMonthsActivity", sessionsArrayList));
    }
}
