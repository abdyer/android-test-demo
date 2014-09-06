package org.andydyer.androidtestdemo;

import android.test.ActivityInstrumentationTestCase2;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.andydyer.androidtestdemo.test.CustomMatchers.withError;

/**
 * Created by andy on 9/6/14.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testEmptyEmailShowsError() {
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.email)).check(matches(withError(
                getActivity().getString(R.string.error_field_required))));
    }

    public void testInvalidEmailShowsError() {
        onView(withId(R.id.email)).perform(typeText("abc"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.email)).check(matches(withError(
                getActivity().getString(R.string.error_invalid_email))));
    }

    public void testShortPasswordShowsError() {
        onView(withId(R.id.email)).perform(typeText("email@host.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("a"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.password)).check(matches(withError(
                getActivity().getString(R.string.error_invalid_password))));
    }

    public void testIncorrectPasswordShowsError() {

        onView(withId(R.id.email)).perform(typeText("email@host.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("abcde"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.password)).check(matches(withError(
                getActivity().getString(R.string.error_incorrect_password))));
    }
}
