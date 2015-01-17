package org.andydyer.androidtestdemo.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;

import static org.mockito.Mockito.mock;

/**
 * Created by andy on 1/15/15.
 */
@Module
public final class DebugApiServiceModule {

    private final boolean mockMode;

    public DebugApiServiceModule(boolean provideMocks) {
        mockMode = provideMocks;
    }

    @Provides @Singleton
    public ApiService provideApiService() {
        if(mockMode) {
            return new MockApiService();
        }
        else {
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();
            return new RestAdapter.Builder()
                    .setEndpoint(ApiService.API_URL)
                    .setConverter(new GsonConverter(gson))
                    .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog("API"))
                    .build()
                    .create(ApiService.class);
        }
    }

    @Provides @Singleton
    public AuthenticationService provideAuthenticationService() {
        if(mockMode) {
            return mock(AuthenticationService.class); // not implemented
        }
        else {
            return null; // not implemented
        }
    }
}
