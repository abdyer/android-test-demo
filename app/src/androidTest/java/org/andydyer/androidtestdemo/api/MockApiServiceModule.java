package org.andydyer.androidtestdemo.api;

import org.andydyer.androidtestdemo.EventListFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.client.Client;

import static org.mockito.Mockito.mock;

/**
 * Created by andy on 8/28/14.
 */
@Module(injects = {EventListFragment.class, MockApiService.class, ApiServiceTest.class})
public class MockApiServiceModule {

    @Provides
    @Singleton
    public ApiService provideApiService() {
        return new MockApiService();
    }
}
