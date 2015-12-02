package com.uni.kevintruong.flextime.managers;

import android.location.Location;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * Created by kwan8 on 2015-12-02.
 */
public class UnitManagerTest extends AndroidTestCase
{
    UnitManager unitManager;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        unitManager = UnitManager.getInstance(context);

    }

    @SmallTest
    public void testGetLocation()
    {
        Location response = unitManager.getLocation();

        assertNotNull(response.getLongitude());
        assertNotNull(response.getLatitude());
        assertNotNull(response.getAccuracy());
        assertNotNull(response.getSpeed());
    }
}
