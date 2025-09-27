package com.framework.hooks;

import com.framework.drivers.DriverFactory;
import com.framework.utils.ModalHandler;
import com.framework.utils.ScreenshotUtil;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.IOException;

public class Hooks {
    @Before
    public void setup() {
        DriverFactory.getDriver();
    }

    @After
    public void teardown(Scenario scenario) throws IOException {
        // Final modal sweep just in case
        ModalHandler.dismissIfPresent(DriverFactory.getDriver());

        ScreenshotUtil screenshotUtil = new ScreenshotUtil(DriverFactory.getDriver());
        String path = screenshotUtil.takeScreenshot("ScenarioEnd");
        if (path != null) {
            ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(path);
            ExtentCucumberAdapter.addTestStepLog("Scenario finished. Screenshot attached: " + path);
        }
        DriverFactory.quitDriver();
    }
}
