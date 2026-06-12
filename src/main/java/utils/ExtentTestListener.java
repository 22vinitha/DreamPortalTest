package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class ExtentTestListener implements ITestListener {

    private static ExtentReports extent =
            ExtentReportManager.getInstance();

    private static ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest t = extent.createTest(
                result.getMethod().getMethodName());
        test.set(t);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        test.get().log(Status.PASS, "✅ Test Passed");

        try {

            Object instance = result.getInstance();

            java.lang.reflect.Field driverField =
                    instance.getClass()
                            .getSuperclass()
                            .getDeclaredField("driver");

            driverField.setAccessible(true);

            WebDriver driver =
                    (WebDriver) driverField.get(instance);

            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            String screenshotDir = "reports/screenshots/";
            new File(screenshotDir).mkdirs();

            String path = screenshotDir
                    + result.getMethod().getMethodName()
                    + "-PASS.png";

            FileUtils.copyFile(src, new File(path));

            test.get().addScreenCaptureFromPath(
                    "screenshots/"
                    + result.getMethod().getMethodName()
                    + "-PASS.png",
                    "Pass Screenshot");

        } catch (Exception e) {

            test.get().warning("Screenshot capture failed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.get().log(Status.FAIL,
                "❌ Test Failed: " + result.getThrowable());

       
        try {
            Object instance = result.getInstance();

            java.lang.reflect.Field driverField =
                    instance.getClass()
                            .getSuperclass()
                            .getDeclaredField("driver");

            driverField.setAccessible(true);

            WebDriver driver =
                    (WebDriver) driverField.get(instance);

            if (driver != null) {

                File src = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE);

                String screenshotDir = "reports/screenshots/";
                new File(screenshotDir).mkdirs();

                String path = screenshotDir
                        + result.getMethod().getMethodName()
                        + "-FAIL.png";

                FileUtils.copyFile(src, new File(path));

                test.get().addScreenCaptureFromPath(
                        "screenshots/"
                        + result.getMethod().getMethodName()
                        + "-FAIL.png",
                        "Failure Screenshot");
            }

        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            test.get().log(Status.WARNING,
                    "Could not capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "⚠️ Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {

        if (extent != null) {

            extent.flush();

            System.out.println("Extent Report Generated Successfully");
        }
    }
    
    }
