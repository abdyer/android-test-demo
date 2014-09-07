package org.andydyer.androidtestdemo.api;

import org.andydyer.androidtestdemo.EventListFragment;
import org.andydyer.androidtestdemo.LoginActivity;
import org.andydyer.androidtestdemo.LoginActivityTest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Created by andy on 8/28/14.
 */
@Module(injects = {
            EventListFragment.class, MockApiService.class, ApiServiceTest.class,
            LoginActivity.class, LoginActivityTest.class
        })
public class MockApiServiceModule {

    @Provides
    @Singleton
    public ApiService provideApiService() {
        return new MockApiService();
    }

    @Provides @Singleton
    public AuthenticationService provideAuthenticationService() {
        return mock(AuthenticationService.class); // not implemented
    }
}
