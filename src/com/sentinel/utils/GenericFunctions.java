package com.sentinel.utils;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;

import com.sentinel.driver.WebDriverTestBase;

public class GenericFunctions extends WebDriverTestBase {

	public static int invisibleExceptionCounter = 0;
	public static Actions action = null;
	public static File file = null;
	public static String exceptionFolder = System.getProperty("user.dir") + "\\ExceptionFiles\\Exception.txt";
	public static Robot robot = null;
	public static int exceptionCounter = 1;
	boolean elementLoaded;
	Actions actions = null;
	ArrayList<String> arrayListValue = null;
	Random random = new Random();
	public static GenericFunctions genericFunctions = new GenericFunctions();
	public static URL link = null;
	public static HttpURLConnection httpConn = null;
	JavascriptExecutor js = null;

	public void waitForPageLoad() {
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}

	public boolean waitForElementVisible(WebElement webElement) {

		elementLoaded = false;
		try {
			for (int i = 0; i < 30; i++) {
				java.util.concurrent.TimeUnit.SECONDS.sleep(2);

				if (webElement.isDisplayed() == true) {
					elementLoaded = true;
					return elementLoaded;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return elementLoaded;
	}

	public boolean waitForElementInvisible(WebElement webElement) {

		elementLoaded = true;
		for (int i = 0; i < 30; i++) {
			try {
				java.util.concurrent.TimeUnit.SECONDS.sleep(1);

				if (webElement.isDisplayed() == false) {
					elementLoaded = true;
					return elementLoaded;
				}
			} catch (Exception e) {
				elementLoaded = true;
				break;
			}
		}
		return elementLoaded;
	}

	public int generateRandomNumber(int minimum, int maximum) {

		int randomNum = minimum + random.nextInt((maximum - minimum) + 1);
		return randomNum;
	}

	public void scrollToElement(WebElement element) {

		actions = new Actions(driver);
		actions.moveToElement(element);
		actions.perform();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public String generaterandomString(int stringLength) {

		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < stringLength; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;

	}

	public String generateRandomEmail() {
		String email;
		email = "sentinel_" + (System.currentTimeMillis() % 1000000000) + "@mailinator.com";
		return email;
	}

	public String getCurrentDate() {

		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		return date;

	}

	public String screenshotOnFailure(String fileName) {
		String screenshotFilePath = null, test = null;
		link = null;
		File scrFile = ((TakesScreenshot) WebDriverTestBase.driver).getScreenshotAs(OutputType.FILE);
		try {
			Thread.sleep(2000);
			screenshotFilePath = System.getProperty("user.dir") + "\\CustomizedReports\\projectScreenshots\\" + fileName
					+ "_" + exceptionCounter + ".jpg";
			// System.out.println("screenshot path :-" + screenshotFilePath);
			FileUtils.copyFile(scrFile, new File(screenshotFilePath));
			exceptionCounter++;
			String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";
			System.setProperty(ESCAPE_PROPERTY, "false");
			link = new File(screenshotFilePath).toURI().toURL();
			test = "<a href=" + link + "> click to open screenshot of " + fileName + "</a>";
			// System.out.println(screenshotFilePath);
			screenshotPathsList.put(fileName, screenshotFilePath);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return test;
	}

	public void completescreenshotOnFailure(String fileName) {
		String completeScreenPhoto = System.getProperty("user.dir") + "\\CompleteScreenshots\\";
		try {
			file = new File(completeScreenPhoto);
			if (!file.exists()) {
				file.mkdir();
			}
			robot = new Robot();
			BufferedImage screenShot = robot
					.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(screenShot, "JPG", new File(file + "\\" + fileName + "E_NO_" + exceptionCounter + ".jpg "));
			exceptionCounter++;
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public boolean verifyElementPresent(WebElement element) {

		elementLoaded = false;
		try {
			if (element.isDisplayed() == true) {
				if ((element.isDisplayed())) {
					elementLoaded = true;
				} else {
					elementLoaded = false;
				}
			}
		} catch (Exception e) {
			elementLoaded = false;
		}
		return elementLoaded;

	}

	public boolean verifyElementDisplayedOnDOM(WebElement element) {

		elementLoaded = false;
		try {
			if (element.isDisplayed() == true) {
				elementLoaded = true;
			}
		} catch (Exception e) {
			elementLoaded = false;
		}
		return elementLoaded;
	}

	public int verifyLink(String urlLink) throws IOException {
		try {
			link = new URL(urlLink);
			httpConn = (HttpURLConnection) link.openConnection();
			httpConn.setConnectTimeout(2000);
			// connect using connect method
			httpConn.connect();
			// use getResponseCode() to get the response code.
			if (httpConn.getResponseCode() == 200) {
				// System.out.println(urlLink + " - " +
				// httpConn.getResponseMessage());
			}
			if (httpConn.getResponseCode() == 404) {
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return httpConn.getResponseCode();
	}

	public void logEvent(String eventLog) {
		logger.debug(eventLog);
		Reporter.log(eventLog + "<br>");

	}

	public static void highlightElement(WebElement element) {
		GenericFunctions genericFunctions = new GenericFunctions();
		genericFunctions.js = (JavascriptExecutor) driver;
		for (int i = 0; i < 4; i++) {
			if (i % 2 == 0) {
				genericFunctions.js.executeScript("arguments[0].setAttribute('style', ' border: 2px solid red;');",
						element);

			} else {
				genericFunctions.js.executeScript("arguments[0].setAttribute('style','border: 1px thin white;');",
						element);
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void navigateToNewTab() {
		try {
			js = (JavascriptExecutor) driver;
			js.executeScript("window.open()");
			arrayListValue = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window((String) arrayListValue.get(1));
			WebDriverTestBase.driver.get(config.getProperty("emailSite"));
			Thread.sleep(2000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String replaceSpecialCharacters(String stringValue, String[] specialCharArray) {
		for (int i = 0; i < specialCharArray.length; i++) {
			if (stringValue.indexOf(specialCharArray[i]) != 0) {
				stringValue = stringValue.replace(specialCharArray[i], "");
			}
		}
		stringValue = stringValue.replace("null", "");
		return stringValue;
	}

	public String generateCustomizedRandomString(int length, String stringValue) {
		final String characters = stringValue;
		StringBuilder result = new StringBuilder();
		while (length > 0) {
			Random rand = new Random();
			result.append(characters.charAt(rand.nextInt(characters.length())));
			length--;
		}

		return result.toString();
	}

	public String generateCurrentDateAndTime(String dateformat) {
		/**
		 * date format - yyyy/MM/dd HH:mm:ss
		 */
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateformat);
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public void createTestCassesTotalTable(StringBuilder htmlStringBuilder) {

		// append table
		if (totalTestCases > 0) {
			htmlStringBuilder.append("<table style=\"float: left\"  border=\"1\" bordercolor=\"#000000\">");

			htmlStringBuilder.append(
					"<TH colspan=\"3\" \"width:150px\" align=\"LEFT\" BGCOLOR=\"#B0C4DE\"><FONT SIZE=4>TOTAL TEST CASES EXECUTED</FONT></TH>");

			htmlStringBuilder
					.append("<tr><td><b>Test Id</b></td><td><b>Test Suite</b></td><td><b>Test Name</b></td></tr>");

			// Total Test Cases

			Map<Object, Object> map = totalTestCasesList;
			Iterator<Map.Entry<Object, Object>> totalTestCasesIterator = map.entrySet().iterator();
			Map<Object, Object> map1 = classNameMethodNameList;
			Iterator<Map.Entry<Object, Object>> classNameListIterator = map1.entrySet().iterator();
			while (totalTestCasesIterator.hasNext()) {
				while (classNameListIterator.hasNext()) {
					Map.Entry<Object, Object> entry = totalTestCasesIterator.next();
					Map.Entry<Object, Object> entry1 = classNameListIterator.next();
					htmlStringBuilder.append("<tr><td>" + entry.getKey() + "</td><td>" + entry1.getValue() + "</td><td>"
							+ entry.getValue() + "</td></tr>");

				}
			} // close html file
			htmlStringBuilder.append("</table>");
		} else {
			htmlStringBuilder.append("<table border=\"5\" bordercolor=\"#000000\">");
			htmlStringBuilder.append(
					"<TR WIDTH=228 ALIGN=LEFT BGCOLOR=\"#EEEEEE\"><FONT SIZE=4>NO TEST CASES EXECUTED</FONT></TR>");

			htmlStringBuilder.append("</table>");
		}

	}

	public void createTestCassesPassedTable(StringBuilder htmlStringBuilder) {
		if (testCasesPassed > 0) {
			// append table
			htmlStringBuilder.append("<table border=\"1\" margin-right: 5% bordercolor=\"#000000\">");
			// append row
			htmlStringBuilder.append(
					"<TH  colspan=\"4\" \"width:90px\" align=\"LEFT\" BGCOLOR=\"#B0C4DE\"><FONT SIZE=4>TEST CASES PASSED</FONT></TH>");

			htmlStringBuilder.append(
					"<tr><td><b>Test Id</b></td><td><b>Test Suite</b></td><td><b>Test Name</b></td><td><b>Test Result</b></td></tr>");
			// append row
			// append row dynamically
			// Total Test Cases
			Map<Object, Object> map1 = totalTestCasesPassedList;
			Iterator<Map.Entry<Object, Object>> testCasesPassedIterator = map1.entrySet().iterator();
			while (testCasesPassedIterator.hasNext()) {
				Map.Entry<Object, Object> entry = testCasesPassedIterator.next();
				// System.out.println("Pass Test Method Class - " +
				// classNameMethodNameList.get(entry.getValue()));
				htmlStringBuilder.append(
						"<tr><td>" + entry.getKey() + "</td><td>" + classNameMethodNameList.get(entry.getValue())
								+ "</td><td>" + entry.getValue() + "</td><td bgcolor=\"#00FF00\">Passed</td></tr>");
			}

			// close table
			htmlStringBuilder.append("</table>");

		}

	}

	public void createTestCassesFailedTable(StringBuilder htmlStringBuilder) {
		if (testCasesFailed > 0) {

			// append table
			htmlStringBuilder.append("<table border=\"1\" bordercolor=\"#000000\">");

			// append row
			htmlStringBuilder.append(
					"<TH colspan=\"5\"  \"width:170px\" align=\"LEFT\" BGCOLOR=\"#B0C4DE\"><FONT SIZE=4>TEST CASES FAILED</FONT></TH>");

			htmlStringBuilder.append(
					"<tr><td><b>Test Id</b></td><td><b>Test Suite</b></td><td><b>Test Name</b></td><td><b>Test Result</b></td><td><b>ScreenshotsOnFailure</b></td></tr>");
			// append row
			// append row dynamically
			// Total Test Cases
			Map<Object, Object> map2 = totalTestCasesFailedList;
			Iterator<Map.Entry<Object, Object>> testCasesFailedIterator = map2.entrySet().iterator();
			while (testCasesFailedIterator.hasNext()) {
				Map.Entry<Object, Object> entry = testCasesFailedIterator.next();
				// System.out.println("Failed Test Case
				// -"+classNameMethodNameList.get(entry.getValue()));
				htmlStringBuilder.append("<tr>" + "<td>" + entry.getKey() + "</td>" + "<td>"
						+ classNameMethodNameList.get(entry.getValue()) + "</td>" + "<td>" + entry.getValue() + "</td>"
						+ "<td bgcolor=\"#FF4500\">Failed</td>" + "</td>" + "<td><a  target=\"_blank\"  href="
						+ screenshotPathsList.get(entry.getValue()) + ">Open Screenshot" + "</a></td></tr>");
				// System.out.println(entry.getKey() + "-------------" +
				// entry.getValue());
			}

			// close html file
			htmlStringBuilder.append("</table>");

		}
	}

	public void createTestCassesPassingPercentageTable(StringBuilder htmlStringBuilder) {
		if (totalTestCases > 0) {

			Float temp;
			Float percentage;

			// append table
			htmlStringBuilder.append("<table border=\"1\" bordercolor=\"#000000\">");
			temp = (float) testCasesPassed / totalTestCases * 100;
			// System.out.println("Temp:---" + temp);
			percentage = temp;
			// System.out.println("Test Case Passing Percentage :- " +
			// percentage);

			// append row #ADFF2F
			htmlStringBuilder
					.append("<TD \"width:150px\" ALIGN=LEFT BGCOLOR=\"#FFFFFF\"><FONT SIZE=4> Total Test Cases :- "
							+ totalTestCases + "</FONT></TD>");

			htmlStringBuilder
					.append("<TD \"width:150px\" ALIGN=LEFT BGCOLOR=\"#00FF00\"><FONT SIZE=4> Test Case Passed :- "
							+ testCasesPassed + "</FONT></TD>");

			htmlStringBuilder
					.append("<TD \"width:150px\" ALIGN=LEFT BGCOLOR=\"#FF4500\"><FONT SIZE=4> Test Case Failed :- "
							+ testCasesFailed + "</FONT></TD>");
			if (percentage >= 70) {
				htmlStringBuilder
						.append("<TD \"width:150px\" ALIGN=LEFT BGCOLOR=\"#00FF00\"><FONT SIZE=4> Test Case Passing Percentage :- "
								+ percentage + "%.</FONT></TD>");

			} else {

				htmlStringBuilder
						.append("<TD \"width:150px\" ALIGN=LEFT BGCOLOR=\"#FF4500\"><FONT SIZE=4> \nTest Case Passing Percentage :- "
								+ percentage + "%.</FONT></TD>");
			}

			// close html file
			htmlStringBuilder.append("</table>");
		}
	}

	public String pieChartReport(int testCasesPassed, int testCasesFailed, int testCasesSkipped) throws IOException {
		// Creating a simple pie chart with
		DefaultPieDataset pieDataset = new DefaultPieDataset();

		// String pieChartPath =
		// "E:\\To_Move\\sentinel\\Sentinel\\SentinelProject\\simplePiechart.jpg";

		String pieChartPath = System.getProperty("user.dir") + File.separator
				+ "CustomizedReports\\PieChart\\simplePiechart.jpg";

		if (testCasesPassed > 0) {
			pieDataset.setValue("PASS", new Integer(testCasesPassed));
		}
		if (testCasesFailed > 0) {
			pieDataset.setValue("FAIL", new Integer(testCasesFailed));
		}
		if (testCasesSkipped > 0) {
			pieDataset.setValue("N/A", new Integer(testCasesSkipped));
		}
		JFreeChart piechart = ChartFactory.createPieChart("Test Case Execution Status", pieDataset, true, true, false);
		try {
			ChartUtilities.saveChartAsJPEG(new File(pieChartPath), piechart, 400, 400);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pieChartPath;
	}

	public void createpieChartTable(StringBuilder htmlStringBuilder) throws IOException {
		if (totalTestCases > 0) {
			// append table
			htmlStringBuilder.append("<table border=\"1\" bordercolor=\"#000000\">");
			// append row
			htmlStringBuilder.append(
					"<TR WIDTH=228 align=\"centre\" BGCOLOR=\"#EEEEEE\"><FONT SIZE=4>Pie Chart Presentation for Test Execution</FONT></TR>");

			htmlStringBuilder.append("<img margin-right: 70% src="
					+ genericFunctions.pieChartReport(testCasesPassed, testCasesFailed, testCasesSkipped)
					+ " alt=\"Pie Chart\">");
			// close table
			htmlStringBuilder.append("</table>");
		}
		genericFunctions.pieChartReport(testCasesPassed, testCasesFailed, testCasesSkipped);
	}

	public static String currentTime() {
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy
		// hh.mm.ss.S aa");
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");

		String formattedDate = dateFormat.format(new Date()).toString();
		// System.out.println(formattedDate);
		return formattedDate;
	}

	public static String currentDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY");

		String formattedDate = dateFormat.format(new Date()).toString();
		// System.out.println(formattedDate);
		return formattedDate;

	}

	public static String currentWeekDay() {

		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
		// System.out.println(day);
		return day;
	}

	public void buildHtmlReport() {
		try {
			// define a HTML String Builder
			StringBuilder htmlStringBuilder = new StringBuilder();
			// append html header and title
			htmlStringBuilder
					.append("<html> <header> <h1 style=\"text-align: center; BGCOLOR=\"#808080\"\">Execution Report<h5 style=\"text-align: right\"> - Generated by Atul Patil with cutomised Report at "
							+ currentTime() + " on " + currentWeekDay() + " on " + currentDate()
							+ " .</h4></h1><h3></h5></header></head>");

			// append body
			htmlStringBuilder.append("<body>");
			/**
			 * TABLE 4
			 */
			createTestCassesPassingPercentageTable(htmlStringBuilder);
			/**
			 * TABLE 3
			 */
			createTestCassesFailedTable(htmlStringBuilder);
			/**
			 * TABLE 2
			 */
			createTestCassesPassedTable(htmlStringBuilder);

			/**
			 * TABLE 1
			 */
			createTestCassesTotalTable(htmlStringBuilder);

			/**
			 * TABLE 5
			 */
			createpieChartTable(htmlStringBuilder);
			// System.out.println("-----------------------------------------------------------------");
			genericFunctions.classNameTestNameMethod(htmlStringBuilder);
			// System.out.println("-----------------------------------------------------------------");
			htmlStringBuilder.append("</body>");
			htmlStringBuilder.append("</html>");
			// write html string content to a file
			genericFunctions.pieChartReport(testCasesPassed, testCasesFailed, testCasesSkipped);
			WriteToFile(htmlStringBuilder.toString(), "report.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void classNameTestNameMethod(StringBuilder htmlStringBuilder) {
		// classNameMethodNameList

		Map<Object, Object> map2 = WebDriverTestBase.classNameMethodNameList;
		Iterator<Map.Entry<Object, Object>> testCasesFailedIterator = map2.entrySet().iterator();
		while (testCasesFailedIterator.hasNext()) {
			Map.Entry<Object, Object> entry = testCasesFailedIterator.next();

			/*
			 * htmlStringBuilder.append("<tr><td>" + entry.getKey() +
			 * "</td><td>" + entry.getValue() +
			 * "</td><td bgcolor=\"#FF4500\">Failed</td>" +
			 * "</td><td><a  target=\"_blank\"  href=" +
			 * screenshotPathsList.get(entry.getValue()) + ">Open Screenshot" +
			 * "</a></td></tr>");
			 */

			// System.out.println(entry.getKey() + "-------------" +
			// entry.getValue());
		}

	}

	public static void WriteToFile(String fileContent, String fileName) throws IOException {
		String projectPath = System.getProperty("user.dir");
		String tempFile = projectPath + File.separator + "CustomizedReports" + File.separator + fileName;
		// System.out.println("REPORT PATH:------" + tempFile);
		File file = new File(tempFile);
		// System.out.println("check file :---" + file.exists());
		// if file does exists, then delete and create a new file
		if (!file.exists()) {
			try {
				File newFileName = new File(projectPath + File.separator + "backup_" + fileName);
				file.renameTo(newFileName);
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// write to file with OutputStreamWriter
		OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		writer.write(fileContent);
		writer.close();

	}

}
