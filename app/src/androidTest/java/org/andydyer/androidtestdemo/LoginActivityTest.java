package org.andydyer.androidtestdemo;

import android.test.ActivityInstrumentationTestCase2;

import org.andydyer.androidtestdemo.api.AuthenticationService;
import org.andydyer.androidtestdemo.api.MockApiServiceModule;
import org.andydyer.androidtestdemo.ui.LoginActivity;
import org.mockito.ArgumentCaptor;

import javax.inject.Inject;

import dagger.ObjectGraph;
import retrofit.Callback;
import retrofit.RetrofitError;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.andydyer.androidtestdemo.test.CustomMatchers.withError;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by andy on 9/6/14.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    @Inject AuthenticationService authenticationService;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ObjectGraph graph = ObjectGraph.create(new MockApiServiceModule());
        graph.inject(this);
        graph.inject(getActivity());
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

        final ArgumentCaptor<Callback> captor = ArgumentCaptor.forClass(Callback.class);
        verify(authenticationService).login(anyString(), anyString(), anyString(), captor.capture());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                captor.getValue().failure(RetrofitError.unexpectedError("", new Exception()));
            }
        });

        onView(withId(R.id.password)).check(matches(withError(
                getActivity().getString(R.string.error_incorrect_password))));
    }
}
