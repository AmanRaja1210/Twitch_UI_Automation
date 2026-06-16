package com.framework.stepdefinitions;

import com.framework.drivers.DriverFactory;
import com.framework.utils.BaseHelper;
import com.framework.utils.ModalHandler;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TwitchSteps {
    private final WebDriver driver = DriverFactory.getDriver();
    private final BaseHelper helper = new BaseHelper(driver);

    // MOBILE-FIRST LOCATORS
    private static final By BROWSE_LINK_DIRECTORY = By.xpath("//a[@href='/directory']");
    private static final By SEARCH_ICON_BUTTON   = By.xpath("//button[@aria-label='Search']");
    private static final By SEARCH_LINK          = By.cssSelector("a[href='/search']");
    private static final By SEARCH_INPUT         = By.xpath("//input[@type='search' or @placeholder='Search']");

    private static final By STREAMER_CARD = By.xpath("(//a[contains(@href,'/videos/') or contains(@href,'/channel/') or contains(@href,'/profile')])[1]");
    private static final By VIDEO_PLAYER  = By.tagName("video");

    @Given("I open Twitch website")
    public void openTwitch() throws IOException {
        driver.get("https://m.twitch.tv/");
        helper.attachScreenshot("Opened Twitch mobile site");
    }

    private void openSearchEntry() throws IOException {
        // If search input is already visible, do nothing
        if (helper.isPresent(SEARCH_INPUT)) {
            helper.attachScreenshot("Search input visible on home");
            return;
        }
        // Try /directory (mobile 'Browse')
        if (helper.isPresent(BROWSE_LINK_DIRECTORY)) {
            helper.click(BROWSE_LINK_DIRECTORY, "Opened Browse (/directory)");
            return;
        }
        // Try search link
        if (helper.isPresent(SEARCH_LINK)) {
            helper.click(SEARCH_LINK, "Opened Search via link");
            return;
        }
        // Try search icon button
        if (helper.isPresent(SEARCH_ICON_BUTTON)) {
            helper.click(SEARCH_ICON_BUTTON, "Opened Search via icon");
        }
    }

    @When("I click on browse")
    public void clickBrowse() throws IOException {
        // Be resilient: open any available search entry point
        openSearchEntry();
    }

    @When("I input {string}")
    public void inputSearch(String game) throws IOException {
        helper.type(SEARCH_INPUT, game, "Entered search term");
    }

    @And("I scroll down {int} times")
    public void scrollDownNTimes(int times) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < times; i++) {
            js.executeScript("window.scrollBy(0, document.body.scrollHeight/2);");
            try {
                Thread.sleep(1000); // small pause to allow content load
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }



    @When("I select one streamer")
    public void selectOneStreamer() throws IOException {
        helper.click(STREAMER_CARD, "Selected first result");
    }

    @Then("I take a screenshot after page is loaded")
    public void takeScreenshotAfterPageLoaded() throws IOException {
        // Dismiss any modal (mature content etc.)
        ModalHandler.dismissIfPresent(driver);

        // Wait for the <video> or keep screenshotting the state
        helper.waitForAnyVisible(VIDEO_PLAYER, SEARCH_INPUT);
        helper.attachScreenshot("Final page state (video/search)");
    }

    @And("I select Data from DataTable")
    public void iSelectDataFromDataTable(DataTable datatble) {

        List<Map<String, String>> data= datatble.asMaps(String.class,String.class);
        Map<String,String> data2= data.get(1);
        String username= data2.get("username");
        String password= data2.get("password");

        System.out.println(username);
        System.out.println(password);

    }
}
