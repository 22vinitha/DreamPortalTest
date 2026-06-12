package tests;

import java.time.Duration;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.DiaryPage;
import pages.HomePage;
import pages.SummaryPage;
import utils.AIValidator;
import utils.ScreenshotUtil;

public class DreamTest extends BaseTest {

    private static final String HOME_URL  = "https://arjitnigam.github.io/myDreams/";
    private static final String DIARY_URL = "https://arjitnigam.github.io/myDreams/dreams-diary.html";

    private void waitForTable(WebDriverWait wait) {

        By rows = By.cssSelector("table tbody tr");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("table tbody")));

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(rows, 4));

        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, 0)");

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(rows, 9));
    }

    // ---------------- HOME PAGE ----------------
    @Test(priority = 1)
    public void testHomePage() {

        HomePage home = new HomePage(driver);
        home.open();

        home.verifyLoadingAnimationAppearsAndDisappears();
        home.verifyMainContentVisible();

        home.clickMyDreams();

        System.out.println("\nHome page verified");
    }

    // ---------------- DIARY PAGE ----------------
    @Test(priority = 2)
    public void testDiaryPage() {

        driver.get(DIARY_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        waitForTable(wait);

        List<String[]> entries = new DiaryPage(driver).getAllDreamEntries();

        Assert.assertEquals(entries.size(), 10,
                "Expected exactly 10 dream entries");

        Map<String, Integer> freq = new HashMap<>();

        for (String[] e : entries) {

            String name = e[0];
            String days = e[1];
            String type = e[2];

            Assert.assertFalse(name.isEmpty() || days.isEmpty() || type.isEmpty(),
                    "Empty column detected");

            Assert.assertTrue(type.equals("Good") || type.equals("Bad"),
                    "Invalid dream type detected");

            freq.put(name, freq.getOrDefault(name, 0) + 1);
        }

        long recurring = freq.values().stream().filter(v -> v > 1).count();

        Assert.assertEquals(recurring, 2,
                "Expected exactly 2 recurring dreams");

        Assert.assertTrue(freq.getOrDefault("Flying over mountains", 0) > 1);
        Assert.assertTrue(freq.getOrDefault("Lost in maze", 0) > 1);

        ScreenshotUtil.captureScreenshot(driver, "dream-diary");

      
        /// ---------------- AI OUTPUT ----------------

System.out.println("\n=========== AI RESPONSE ===========");

boolean fallbackUsed = false;
List<String> results = new ArrayList<>();

for (String[] e : entries) {

    String dream = e[0];
    String expected = e[2]; // table value (Good/Bad)

    String ai = AIValidator.classifyDream(dream);

    if (AIValidator.isAIFailed()) {
        fallbackUsed = true;
    }

    boolean match = ai.equalsIgnoreCase(expected);

    results.add(
            dream +
            " | Expected=" + expected +
            " | AI=" + ai +
            " | Match=" + match
    );
}

// AI status (once)
System.out.println(AIValidator.isAIFailed()
        ? "API FAILED / QUOTA EXCEEDED"
        : "AI SUCCESS");

// fallback status (once)
System.out.println("\n=========== FALLBACK MODE ===========");

if (fallbackUsed) {
    System.out.println("ACTIVE (AI FAILED OR QUOTA EXCEEDED → USING FALLBACK LOGIC)");
} else {
    System.out.println("AI WORKING NORMALLY");
}

// results
System.out.println("\n=========== RESULTS ===========");

for (String r : results) {
    System.out.println(r);
}
    }
    // ---------------- SUMMARY PAGE ----------------
    @Test(priority = 3)
    public void testSummaryPage() {

        driver.get(DIARY_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        waitForTable(wait);

        List<String[]> entries = new DiaryPage(driver).getAllDreamEntries();

        SummaryPage summary = new SummaryPage(entries);

        int good      = summary.getGood();
        int bad       = summary.getBad();
        int total     = summary.getTotal();
        int recurring = summary.getRecurring();

        System.out.println(
                "\nGood=" + good +
                " Bad=" + bad +
                " Total=" + total +
                " Recurring=" + recurring
        );

        Assert.assertEquals(total, 10);
        Assert.assertEquals(good, 6);
        Assert.assertEquals(bad, 4);
        Assert.assertEquals(recurring, 2);

        Assert.assertEquals(good + bad, total);

        ScreenshotUtil.captureScreenshot(driver, "summary");
    }
}