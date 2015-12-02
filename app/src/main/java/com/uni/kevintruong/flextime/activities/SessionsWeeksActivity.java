package com.uni.kevintruong.flextime.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.managers.SessionManager;
import com.uni.kevintruong.flextime.models.Session;
import java.util.ArrayList;

public class SessionsWeeksActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions_mock);

        ArrayList<Session> sessionsArrayList = this.getIntent().getParcelableArrayListExtra("sessions");

        //Mocks Data
        TextView mockTitle = (TextView) findViewById(R.id.mockTitle);
        mockTitle.setText("2015");
        TextView mock1 = (TextView) findViewById(R.id.textMock);
        mock1.setText("#48");
        TextView mock2 = (TextView) findViewById(R.id.textMock2);
        mock2.setText("20h 19m");
        TextView mock3 = (TextView) findViewById(R.id.textMock3);
        mock3.setText("#47");
        TextView mock4 = (TextView) findViewById(R.id.textMock4);
        mock4.setText("23h 29m");
        TextView mock5 = (TextView) findViewById(R.id.textMock5);
        mock5.setText("#46");
        TextView mock6 = (TextView) findViewById(R.id.textMock6);
        mock6.setText("42h 27m");

        LinearLayout includedLayout = (LinearLayout) findViewById(R.id.includedSessionsMenuBtns);

        Button singleBtn = (Button) includedLayout.findViewById(R.id.sessionSingle);
        Button daysBtn = (Button) includedLayout.findViewById(R.id.sessionDay);
        Button weeksBtn = (Button) includedLayout.findViewById(R.id.sessionWeeks);
        weeksBtn.setBackgroundColor(Color.parseColor("#33b5e5"));
        weeksBtn.setTextColor(Color.parseColor("#212121"));
        Button monthsBtn = (Button) includedLayout.findViewById(R.id.sessionMonths);

        SessionManager sm = new SessionManager(getApplicationContext());

        singleBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsSingleActivity", sessionsArrayList));
        daysBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsDaysActivity", sessionsArrayList));
        monthsBtn.setOnClickListener(sm.sessionsBtnListener(getApplicationContext(), "com.uni.kevintruong.flextime.SessionsMonthsActivity", sessionsArrayList));
    }

}
