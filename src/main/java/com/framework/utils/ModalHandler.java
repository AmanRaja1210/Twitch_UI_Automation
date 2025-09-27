package com.framework.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ModalHandler {

    private static final List<By> POSSIBLE_BUTTONS = Arrays.asList(
            By.xpath("//button[contains(.,'Start Watching')]"),
            By.xpath("//button[contains(.,'Allow')]"),
            By.xpath("//button[contains(.,'I Understand')]"),
            By.xpath("//button[contains(.,'Got it')]")
    );

    public static void dismissIfPresent(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        for (By locator : POSSIBLE_BUTTONS) {
            try {
                WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                if (btn.isDisplayed()) {
                    btn.click();
                    return;
                }
            } catch (Exception ignored) { }
        }
    }
}
