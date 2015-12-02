package com.uni.kevintruong.flextime.activities;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ListView;
import com.uni.kevintruong.flextime.R;

/**
 * Created by kwan8 on 2015-12-02.
 */
public class MyLocationsActivityTest extends ActivityInstrumentationTestCase2<MyLocationsActivity>
{
    MyLocationsActivity myLocationsActivity;

    public MyLocationsActivityTest()
    {
        super(MyLocationsActivity.class);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        myLocationsActivity = getActivity();
    }

    @SmallTest
    public void testMyLocationsListView()
    {
        ListView myLocationsListview = (ListView) myLocationsActivity.findViewById(R.id.myLocationList);

        assertNotNull(myLocationsListview);
    }
}
