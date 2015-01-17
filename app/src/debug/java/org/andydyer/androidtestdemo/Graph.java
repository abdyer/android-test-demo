package org.andydyer.androidtestdemo;

import org.andydyer.androidtestdemo.api.DebugApiServiceModule;
import org.andydyer.androidtestdemo.ui.LoginActivity;
import org.andydyer.androidtestdemo.ui.fragments.EventListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by andy on 1/15/15.
 */
@Singleton
@Component(modules = {DebugApiServiceModule.class})
public interface Graph {

    // In a real app, we'd minimize the number of inject overloads by using base classes for fragments, activities, etc.
    void inject(InjectedActivityTest activity);
    void inject(InjectedInstrumentationTest testCase);
    void inject(LoginActivity activity);
    void inject(EventListFragment fragment);

    public final static class Initializer {
        public static Graph init(boolean mockMode) {
            return Dagger_Graph.builder()
                    .debugApiServiceModule(new DebugApiServiceModule(mockMode))
                    .build();
        }
    }
}
