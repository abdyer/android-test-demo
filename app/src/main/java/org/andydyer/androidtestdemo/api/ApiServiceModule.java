package org.andydyer.androidtestdemo.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.andydyer.androidtestdemo.EventListFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;

/**
 * Created by andy on 8/23/14.
 */
@Module(injects = EventListFragment.class)
public class ApiServiceModule {

    @Provides @Singleton
    public ApiService provideApiService() {
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
