package com.uni.kevintruong.flextime.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.managers.SessionManager;
import com.uni.kevintruong.flextime.models.Session;

import java.util.ArrayList;

public class SessionsDaysActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions_mock);

        ArrayList<Session> sessionsArrayList = this.getIntent().getParcelableArrayListExtra("sessions");

        //Mock
        LinearLayout includedLayout = (LinearLayout) findViewById(R.id.includedSessionsMenuBtns);

        Button singleBtn = (Button) includedLayout.findViewById(R.id.sessionSingle);
        Button daysBtn = (Button) includedLayout.findViewById(R.id.sessionDay);
        Button weeksBtn = (Button) includedLayout.findViewById(R.id.sessionWeeks);
        Button monthsBtn = (Button) includedLayout.findViewById(R.id.sessionMonths);

        SessionManager sm = SessionManager.getInstance();

        singleBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsSingleActivity", sessionsArrayList, "single"));
        weeksBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsWeeksActivity", sessionsArrayList,"weeks"));
        monthsBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsMonthsActivity", sessionsArrayList, "months"));
    }
}
