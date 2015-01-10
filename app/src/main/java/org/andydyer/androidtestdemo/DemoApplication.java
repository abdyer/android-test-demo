package org.andydyer.androidtestdemo;

import android.app.Application;

import lombok.Getter;
import lombok.NonNull;

/**
 * Created by andy on 8/23/14.
 */
public class DemoApplication extends Application {

    private dagger.ObjectGraph objectGraph;

    @Getter static DemoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        objectGraph = dagger.ObjectGraph.create(Modules.list());
    }

    public void inject(@NonNull Object dependent) {
        objectGraph.inject(dependent);
    }
}
