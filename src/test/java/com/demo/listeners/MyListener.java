package com.demo.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyListener implements ITestListener{
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Test Started: "+result.getName());
	}
	
	@Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("❌ Test Failed: " + result.getName());
        // You can add screenshot logic here
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⚠️ Test Skipped: " + result.getName());
    }

}
