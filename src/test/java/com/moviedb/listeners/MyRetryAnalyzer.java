package com.moviedb.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import api.utils.AuthManager;

public class MyRetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private static final int MAX_RETRY = 1; // Retry only once

    @Override
    public boolean retry(ITestResult result) {
        // 1. Only retry if we haven't hit our limit
        if (count < MAX_RETRY) {
            System.out.println("Test failed. Checking for 401 to trigger retry...");
            Throwable throwable = result.getThrowable();
            
            // 2. Check if the failure was specifically due to a 401 status code
            if (throwable != null && isAuthFailure(throwable.getMessage())) {
                System.out.println("[RETRY] 401 detected in test: " + result.getName());
                System.out.println("Retry attempt #" + (count + 1));
                // 3. Clear cache to force a fresh login on the next attempt
                AuthManager.clearCache(); 

                count++;
                return true; // Triggers the retry
            }
        }
        return false;
    }
    
    private boolean isAuthFailure(String message) {
        // Checks for the standard RestAssured failure string or manual "401"
        return message != null && (message.contains("was <401>") || message.contains("401"));
    }
}
