package org.andydyer.androidtestdemo.test;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.andydyer.androidtestdemo.R;
import org.hamcrest.Matcher;

/**
 * Created by andy on 1/10/15.
 */
public class CustomViewActions {

    public static ViewAction clickEventItemAvatar() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.findViewById(R.id.event_list_item_avatar).callOnClick();
            }
        };
    }
}
