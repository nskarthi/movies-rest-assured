package com.moviedb.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager implements ITestListener, ISuiteListener {
	private static ExtentReports extent;
	// This is for parallel testing
	private static ThreadLocal<ExtentTest> methodTest = new ThreadLocal<>();

	@Override
    public void onStart(ISuite suite) {
        // Runs ONCE before the entire suite starts
        ExtentSparkReporter spark = new ExtentSparkReporter("target/MovieDB_Report.html");
        spark.config().setReportName("Movie Database API Regression");
        
        extent = new ExtentReports();
        extent.attachReporter(spark);
	}

	@Override
	public void onTestStart(ITestResult result) {
        // 1. Add @Test Description to the report
        String description = result.getMethod().getDescription();
        
        // 2. Create the test in ExtentReports
        // Overload: createTest(name, description)
        ExtentTest test = extent.createTest(result.getMethod().getMethodName(), description);
        
        // 3. Group by <test> tag name from testng.xml
        // This adds a "Category" to the test, allowing you to filter by "Movie related tests" vs "System related tests"
        String testContextName = result.getTestContext().getName();
        test.assignCategory(testContextName);

        // 4. Identify by @Test(groups = {"actor", "movie"})
        String[] groups = result.getMethod().getGroups();
        if (groups != null && groups.length > 0) {
            for (String group : groups) {
                test.assignCategory(group); // Adds each group as a filterable category
            }
        }
        
        // 5. Assign the test object to the current thread
		methodTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		methodTest.get().pass("Test Passed successfully");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		methodTest.get().fail(result.getThrowable()); // Automatically logs the error/stacktrace
	}

    @Override
    public void onFinish(ISuite suite) {
        // Runs ONCE after the entire suite completes
        if (extent != null) {
            extent.flush();
        }
    }

	// Static helper to log from anywhere in your code
	public static void log(String message) {
		if (methodTest.get() != null) {
			methodTest.get().info(message);
		} else {
			// Fallback to console if ExtentTest isn't initialized yet
			System.out.println("[INFO] " + message);
		}
	}
}
