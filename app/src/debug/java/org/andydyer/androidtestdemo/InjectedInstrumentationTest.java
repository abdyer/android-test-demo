package org.andydyer.androidtestdemo;

import android.test.InstrumentationTestCase;

import org.andydyer.androidtestdemo.api.ApiService;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by andy on 1/16/15.
 */
public class InjectedInstrumentationTest extends InstrumentationTestCase {

    @Inject @Getter ApiService apiService;

    @Override
    protected void setUp() throws Exception {
        DemoApplication.getInstance().setMockMode(true);
        DemoApplication.getInstance().getGraph().inject(this);
    }

    @Override
    protected void tearDown() throws Exception {
        DemoApplication.getInstance().setMockMode(false);
    }
}
