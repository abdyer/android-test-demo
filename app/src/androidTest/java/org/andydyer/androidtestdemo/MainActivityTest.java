package org.andydyer.androidtestdemo;

import android.test.ActivityInstrumentationTestCase2;

import org.andydyer.androidtestdemo.api.Event;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.andydyer.androidtestdemo.test.CustomMatchers.hasData;
import static org.andydyer.androidtestdemo.test.CustomMatchers.showsUrl;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

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
        onData(is(instanceOf(Event.class))).atPosition(0).perform(click());
        onView(withId(R.id.webview)).check(matches(showsUrl("http://github.com/google/iosched")));
    }

    public void testImageClickLaunchesProfileUrl() throws Exception {
        onData(is(instanceOf(Event.class))).atPosition(0)
                .onChildView(withId(R.id.event_list_item_avatar)).perform(click());
        onView(withId(R.id.webview)).check(matches(showsUrl("http://github.com/karthikraj-duraisamy")));
    }
}
