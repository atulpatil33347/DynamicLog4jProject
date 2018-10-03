package com.sentinel.listeners;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.testng.IAnnotationTransformer;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.sentinel.driver.WebDriverTestBase;
import com.sentinel.utils.GenericFunctions;
import com.sentinel.utils.SoftAssertUtil;

public class ProjectListeners implements ITestListener, IExecutionListener {

	public static File file = null;
	GenericFunctions genericFunctions = new GenericFunctions();
	// public static int totalTestCases, testCasesPassed, testCasesFailed,
	// testCasesSkipped;
	public String stringValue = null;
	
	@Override
	public void onTestStart(ITestResult result) {
		/**
		 * Initiate Listener
		 */
		new SoftAssertUtil();

		WebDriverTestBase.totalTestCases++;

		stringValue = result.getName();
		// System.out.println(stringValue);
		WebDriverTestBase.totalTestCasesList.put(WebDriverTestBase.totalTestCases, stringValue);
		/**
		 * Project Log
		 */
		WebDriverTestBase.logger
				.debug("-----------------------------------------------------------------------------------------");
		WebDriverTestBase.logger.debug("TEST CASE STARTED - " + result.getName().toUpperCase());
		WebDriverTestBase.logger
				.debug("-----------------------------------------------------------------------------------------");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		WebDriverTestBase.testCasesPassed++;
		stringValue = result.getName();
		WebDriverTestBase.totalTestCasesPassedList.put(WebDriverTestBase.testCasesPassed, stringValue);
		// SoftAssertUtil.getAssert().assertAll();
		WebDriverTestBase.logger
				.debug("-----------------------------------------------------------------------------------------");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		WebDriverTestBase.testCasesFailed++;
		stringValue = result.getName();
		// System.out.println(WebDriverTestBase.testCasesFailed + " - " +
		// stringValue);
		WebDriverTestBase.totalTestCasesFailedList.put(WebDriverTestBase.testCasesFailed, stringValue);

		// SoftAssertUtil.getAssert().assertAll();
		WebDriverTestBase.logger
				.debug("****************************************************************************************");
		WebDriverTestBase.logger.debug("TEST CASE FAILED ...." + result.getName());
		WebDriverTestBase.logger
				.debug("****************************************************************************************");

		/*
		 * JavascriptExecutor js = (JavascriptExecutor)
		 * WebDriverTestBase.driver; js.executeScript("scroll(0, -250);");
		 */
		Reporter.log(genericFunctions.screenshotOnFailure(result.getName()) + "<br>");
		Reporter.log("<br>");
		// genericFunctions.completescreenshotOnFailure(result.getName());

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		WebDriverTestBase.testCasesSkipped++;
		// totalTestCasesSkippedList.add(result.getName());

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
	}

	@Override
	public void onFinish(ITestContext context) {

		/*
		 * System.out.println(
		 * "-----------------------------------------------------------------");
		 * genericFunctions.classNameTestNameMethod(); System.out.println(
		 * "-----------------------------------------------------------------");
		 */
	}

	@Override
	public void onExecutionStart() {

	}

	@Override
	public void onExecutionFinish() {
		WebDriverTestBase.logger.debug("Total test Cases - " + WebDriverTestBase.totalTestCases);
		WebDriverTestBase.logger.debug("Total test Cases passed - " + WebDriverTestBase.testCasesPassed);
		WebDriverTestBase.logger.debug("Total test Cases failed - " + WebDriverTestBase.testCasesFailed);
		WebDriverTestBase.logger.debug("Total test Cases skipped- " + WebDriverTestBase.testCasesSkipped);
		GenericFunctions genericFunctions = new GenericFunctions();
		genericFunctions.buildHtmlReport();

	}

}
