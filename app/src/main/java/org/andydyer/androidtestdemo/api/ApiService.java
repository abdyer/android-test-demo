package org.andydyer.androidtestdemo.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by andy on 8/23/14.
 */
public interface ApiService {

    public static final String API_URL = "https://api.github.com/";

    @GET("/orgs/{organization}/events")
    public void getEvents(@Path("organization") String organization, Callback<List<Event>> callback);
}
