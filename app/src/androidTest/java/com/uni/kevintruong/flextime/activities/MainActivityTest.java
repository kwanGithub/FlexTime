package com.uni.kevintruong.flextime.activities;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.TextView;

import com.uni.kevintruong.flextime.R;

/**
 * Created by kwan8 on 2015-12-02.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
    MainActivity mainActivity;

    public MainActivityTest()
    {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        mainActivity = getActivity();
    }

    @SmallTest
    public void testMainTextView()
    {
        TextView mainTextView = (TextView) mainActivity.findViewById(R.id.mainLocationText);

        assertNotNull(mainTextView);
        assertEquals("FLEXTIME", mainTextView.getText());
    }

    @SmallTest
    public void testMainOptionBtn()
    {
        Button mainOptionBtn = (Button) mainActivity.findViewById(R.id.mainOptionsBtn);

        assertNotNull(mainOptionBtn);
        assertEquals("Options", mainOptionBtn.getText());
    }
}
