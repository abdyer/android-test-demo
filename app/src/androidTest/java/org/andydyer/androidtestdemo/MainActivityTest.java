package org.andydyer.androidtestdemo;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.test.ActivityInstrumentationTestCase2;

import org.andydyer.androidtestdemo.ui.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.andydyer.androidtestdemo.test.CustomMatchers.hasData;
import static org.andydyer.androidtestdemo.test.CustomMatchers.showsUrl;
import static org.andydyer.androidtestdemo.test.CustomViewActions.clickEventItemAvatar;

/**
 * Created by andy on 8/18/14.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testHasData() throws Exception {
        onView(withId(android.R.id.list)).check(matches(hasData()));
    }

    public void testRowClickLaunchesRepoUrl() throws Exception {
        onView(withId(android.R.id.list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.webview)).check(matches(showsUrl("github.com/google/iosched")));
    }

    public void testImageClickLaunchesProfileUrl() throws Exception {
        onView(withId(android.R.id.list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickEventItemAvatar()));
        onView(withId(R.id.webview)).check(matches(showsUrl("github.com/karthikraj-duraisamy")));
    }
}
