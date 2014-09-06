package org.andydyer.androidtestdemo.api;

import android.test.InstrumentationTestCase;

import org.andydyer.androidtestdemo.DemoApplication;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by andy on 8/30/14.
 */
public class ApiServiceTest extends InstrumentationTestCase {

    @Inject ApiService service;

    @Override
    protected void setUp() throws Exception {
        DemoApplication.getInstance().inject(this);
    }

    public void testEventsRequest() {
        service.getEvents("google", new Callback<Events>() {
            @Override
            public void success(Events events, Response response) {
                assertNotNull(events);
                assertFalse(events.isEmpty());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
