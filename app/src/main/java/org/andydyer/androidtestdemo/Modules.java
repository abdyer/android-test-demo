package org.andydyer.androidtestdemo;

import org.andydyer.androidtestdemo.api.ApiServiceModule;

/**
 * Created by andy on 9/6/14.
 */
public class Modules {
    static Object[] list() {
        return new Object[] {
            new ApiServiceModule()
        };
    }
}
