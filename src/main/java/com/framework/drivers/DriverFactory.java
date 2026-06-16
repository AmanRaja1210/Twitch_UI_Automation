package com.framework.drivers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            Map<String, String> mobileEmulation = new HashMap<>();
            // Stable built-in device
            mobileEmulation.put("deviceName", "Galaxy S5");

            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("mobileEmulation", mobileEmulation);

            options.addArguments("--remote-allow-origins=*");

            // Required for AWS CodeBuild
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            options.setExperimentalOption("mobileEmulation", mobileEmulation);
            options.addArguments("--remote-allow-origins=*");
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
