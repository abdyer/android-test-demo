package org.andydyer.androidtestdemo;

import android.test.ActivityInstrumentationTestCase2;

import org.andydyer.androidtestdemo.api.AuthenticationService;

import javax.inject.Inject;

/**
 * Created by andy on 1/16/15.
 */
public class InjectedActivityTest extends ActivityInstrumentationTestCase2 {

    @Inject AuthenticationService authenticationService;

    public InjectedActivityTest(Class activityClass) { super(activityClass); }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DemoApplication.getInstance().setMockMode(true);
        DemoApplication.getInstance().getGraph().inject(this);
    }

    @Override
    protected void tearDown() throws Exception {
        DemoApplication.getInstance().setMockMode(false);
    }
}
