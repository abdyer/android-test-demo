package org.andydyer.androidtestdemo.api;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by andy on 9/7/14.
 */
public interface AuthenticationService {

    @POST("/login/oauth/access_token") // authentication is not implemented in this project. this endpoint is here to demonstrate mocking a response for testing.
    public void login(@Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("code") String code, Callback<Boolean> callback);
}
