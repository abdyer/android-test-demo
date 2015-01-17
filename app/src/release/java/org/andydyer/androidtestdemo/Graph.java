package org.andydyer.androidtestdemo;

import android.app.Activity;
import android.support.v4.app.Fragment;

import org.andydyer.androidtestdemo.api.ApiServiceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by andy on 1/15/15.
 */
@Singleton
@Component(modules = {ApiServiceModule.class})
public interface Graph {

    void inject(Activity activity);
    void inject(Fragment fragment);

    public final static class Initializer {
        public static Graph init(boolean mockMode) {
            return Dagger_Graph.builder()
                    .build();
        }
    }
}
