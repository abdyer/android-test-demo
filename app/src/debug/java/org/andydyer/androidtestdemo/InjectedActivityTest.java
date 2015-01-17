package org.andydyer.androidtestdemo;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import org.andydyer.androidtestdemo.api.AuthenticationService;

import javax.inject.Inject;

/**
 * Created by andy on 1/16/15.
 */
public class InjectedActivityTest<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    @Inject AuthenticationService authenticationService;

    public InjectedActivityTest(Class<T> activityClass) { super(activityClass); }

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
