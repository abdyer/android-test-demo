package org.andydyer.androidtestdemo;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.AdapterView;

import org.andydyer.androidtestdemo.api.Event;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
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

    private Matcher<View> hasData() {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof AdapterView)) {
                    return false;
                }
                Adapter adapter = ((AdapterView) view).getAdapter();
                return !adapter.isEmpty();
            }
        };
    }

    private Matcher<View> showsUrl(final String url) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof WebView)) {
                    return false;
                }
                WebView webView = (WebView) view;
                return webView.getUrl().equals(url);
            }
        };
    }
}
