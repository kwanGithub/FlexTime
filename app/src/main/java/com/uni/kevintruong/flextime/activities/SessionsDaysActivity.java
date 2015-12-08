package com.uni.kevintruong.flextime.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.managers.SessionManager;
import com.uni.kevintruong.flextime.models.Session;
import java.util.ArrayList;

/**
 * Handles SessionDaysActivity
 */
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
        daysBtn.setBackgroundColor(Color.parseColor("#33b5e5"));
        daysBtn.setTextColor(Color.parseColor("#212121"));
        Button weeksBtn = (Button) includedLayout.findViewById(R.id.sessionWeeks);
        Button monthsBtn = (Button) includedLayout.findViewById(R.id.sessionMonths);

        SessionManager sm = new SessionManager(getApplicationContext());

        singleBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsSingleActivity", sessionsArrayList));
        weeksBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsWeeksActivity", sessionsArrayList));
        monthsBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsMonthsActivity", sessionsArrayList));
    }
}
