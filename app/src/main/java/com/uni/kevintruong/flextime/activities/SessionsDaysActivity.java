package com.uni.kevintruong.flextime.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.managers.SessionManager;

public class SessionsDaysActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions_mock);

        //Mock
        LinearLayout includedLayout = (LinearLayout) findViewById(R.id.includedSessionsMenuBtns);

        Button singleBtn = (Button) includedLayout.findViewById(R.id.sessionSingle);
        Button daysBtn = (Button) includedLayout.findViewById(R.id.sessionDay);
        Button weeksBtn = (Button) includedLayout.findViewById(R.id.sessionWeeks);
        Button monthsBtn = (Button) includedLayout.findViewById(R.id.sessionMonths);

        SessionManager sm = SessionManager.getInstance();

        singleBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsSingleActivity", "single"));
        weeksBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsWeeksActivity", "weeks"));
        monthsBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsMonthsActivity", "months"));
    }
}
