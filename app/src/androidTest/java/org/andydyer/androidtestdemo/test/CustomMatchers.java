package org.andydyer.androidtestdemo.test;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by andy on 9/6/14.
 */
public class CustomMatchers {

    public static Matcher<View> hasData() {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof RecyclerView)) {
                    return false;
                }
                return ((RecyclerView) view).getAdapter().getItemCount() > 0;
            }
        };
    }

    public static Matcher<View> showsUrl(final String url) {
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
                return webView.getUrl().contains(url);
            }
        };
    }

    public static Matcher<View> withError(final String expected) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof EditText)) {
                    return false;
                }
                EditText editText = (EditText) view;
                return editText.getError().toString().equals(expected);
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }
}
