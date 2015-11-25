package com.uni.kevintruong.flextime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.uni.kevintruong.flextime.R;

public class SessionsMonthsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions_mock);

        TextView mockTitle = (TextView) findViewById(R.id.mockTitle);
        mockTitle.setText("2015");

        TextView mock1 = (TextView) findViewById(R.id.textMock);
        mock1.setText("november");

        TextView mock2 = (TextView) findViewById(R.id.textMock2);
        mock2.setText("121h 14m");

        TextView mock3 = (TextView) findViewById(R.id.textMock3);
        mock3.setText("october");

        TextView mock4 = (TextView) findViewById(R.id.textMock4);
        mock4.setText("141h 34m");

        TextView mock5 = (TextView) findViewById(R.id.textMock5);
        mock5.setText("september");

        TextView mock6 = (TextView) findViewById(R.id.textMock6);
        mock6.setText("160h 28m");
    }
}
