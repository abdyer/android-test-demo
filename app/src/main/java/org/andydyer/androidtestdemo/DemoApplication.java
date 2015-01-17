package org.andydyer.androidtestdemo;

import android.app.Application;

import lombok.Getter;

/**
 * Created by andy on 8/23/14.
 */
public class DemoApplication extends Application {

    @Getter Graph graph;

    @Getter static DemoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        graph = Graph.Initializer.init(false);
    }

    public void setMockMode(boolean useMock) {
        graph = Graph.Initializer.init(useMock);
    }
}
