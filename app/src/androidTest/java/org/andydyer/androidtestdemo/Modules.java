package org.andydyer.androidtestdemo;

import org.andydyer.androidtestdemo.api.MockApiServiceModule;

/**
 * Replace Module class in app with a test-specific implementation that returns mock modules instead.
 */
public class Modules {
    static Object[] list() {
        return new Object[] {
                new MockApiServiceModule()
        };
    }
}
