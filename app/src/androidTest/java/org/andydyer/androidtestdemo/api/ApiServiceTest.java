package org.andydyer.androidtestdemo.api;

import org.andydyer.androidtestdemo.InjectedInstrumentationTest;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by andy on 8/30/14.
 */
public class ApiServiceTest extends InjectedInstrumentationTest {

    public void testEventsRequest() {
        getApiService().getEvents("google", new Callback<Events>() {
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
