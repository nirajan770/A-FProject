package com.abercrombiefitch;


import com.google.android.gms.common.ConnectionResult;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.gms.ShadowGooglePlayServicesUtil;


public class TestBase {
    @Before
    public void setUp() throws Exception {
        // Turn off Google Analytics - Does not need to work anymore
        final ShadowApplication shadowApplication = Shadows.shadowOf(RuntimeEnvironment.application);
        shadowApplication.declareActionUnbindable("com.google.android.gms.analytics.service.START");

        // Force success
        ShadowGooglePlayServicesUtil.setIsGooglePlayServicesAvailable(ConnectionResult.SUCCESS);
    }

    @After
    public void tearDown() throws Exception {

    }
}
