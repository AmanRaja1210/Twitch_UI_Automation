package com.framework.utils;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class BaseHelper {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final ScreenshotUtil screenshotUtil;

    public BaseHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.screenshotUtil = new ScreenshotUtil(driver);
    }

    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForAnyVisible(By... locators) {
        long end = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < end) {
            for (By loc : locators) {
                try {
                    WebElement el = driver.findElement(loc);
                    if (el.isDisplayed()) return el;
                } catch (NoSuchElementException ignored) {}
            }
            try { Thread.sleep(250); } catch (InterruptedException ignored) {}
        }
        throw new TimeoutException("None of the locators became visible within timeout");
    }

    public boolean isPresent(By locator) {
        try {
            return driver.findElements(locator).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void click(By locator, String stepInfo) throws IOException {
        try {
            WebElement element = waitForClickable(locator);
            element.click();
        } catch (ElementClickInterceptedException e) {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
        attachScreenshot(stepInfo);
    }

    public void type(By locator, String text, String stepInfo) throws IOException {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text + Keys.ENTER);
        attachScreenshot(stepInfo + ": " + text);
    }

    public void attachScreenshot(String logMessage) throws IOException {
        String path = screenshotUtil.takeScreenshot("Step");
        ExtentCucumberAdapter.addTestStepLog(logMessage);
        if (path != null) {
            ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(path);
        }
    }
}
