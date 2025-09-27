package com.framework.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    private final WebDriver driver;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");

    public ScreenshotUtil(WebDriver driver) {
        this.driver = driver;
    }

    public String takeScreenshot(String namePrefix) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileName = namePrefix + "_" + Thread.currentThread().getId() + "_" + sdf.format(new Date()) + ".png";
            String dir = System.getProperty("user.dir") + "/target/screenshots/";
            new File(dir).mkdirs();
            File dest = new File(dir + fileName);
            FileUtils.copyFile(src, dest);
            return dest.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
