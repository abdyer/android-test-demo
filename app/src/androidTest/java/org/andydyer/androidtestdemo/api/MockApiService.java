package org.andydyer.androidtestdemo.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Path;
import retrofit.mime.TypedByteArray;

/**
 * Created by andy on 8/31/14.
 */
public class MockApiService implements ApiService {

    private static final int HTTP_OK_STATUS = 200;

    private static final String EVENTS_RESPONSE_FILE = "events_response.json";

    @Override
    public void getEvents(@Path("organization") String organization, Callback<Events> callback) {
        try {
            String json = readFileFromAssets(EVENTS_RESPONSE_FILE);
            Response response = getMockResponse(HTTP_OK_STATUS, json);
            Events events = getMockResponseData(json, Events.class);
            callback.success(events, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFileFromAssets(String fileName) throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("assets/" + fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        return IOUtils.toString(reader);
    }

    private <T> T getMockResponseData(String json, Class<T> klass) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        return gson.fromJson(json, klass);
    }

    private Response getMockResponse(int httpCode, String json) throws IOException {
        return new Response("url", httpCode, "", Collections.EMPTY_LIST,
                new TypedByteArray("application/json", json.getBytes()));
    }
}
