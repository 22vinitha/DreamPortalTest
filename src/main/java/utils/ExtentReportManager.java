package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if (extent == null) {

            
            String reportDir = "reports";
            File dir = new File(reportDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

           
            String timeStamp = new SimpleDateFormat(
                    "yyyyMMdd_HHmmss")
                    .format(new Date());

            String reportPath =
                    reportDir + "/TestReport_"
                            + timeStamp + ".html";

        
            ExtentSparkReporter spark =
                    new ExtentSparkReporter(reportPath);

            
            spark.config().setReportName(
                    "Dream Journal Test Report");

            spark.config().setDocumentTitle(
                    "Automation Results");

            spark.config().setTheme(Theme.DARK);

            
            extent = new ExtentReports();

            extent.attachReporter(spark);

            extent.setSystemInfo("Tester", "Vinitha.M");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("OS", "Windows 11");
            extent.setSystemInfo(
                    "Java Version",
                    System.getProperty("java.version"));

            extent.setSystemInfo(
                    "Execution Time",
                    new Date().toString());
        }

        return extent;
    }
}