package com.demo.commonClasses;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import java.text.SimpleDateFormat;
import java.time.Duration;

import java.util.Calendar;
import java.util.Date;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.Test;

import com.demo.commonClasses.Variables;

public class FuncitonLibrary extends Variables {

	// **************************************************************************************************************************************************
	// LIBRARY Functions
	// **************************************************************************************************************************************************

	// *******************************************************************************
	public String timestamp() {
		return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
	}

	public String ScreenshotPath() {
		String scrnshtPth = System.getProperty("user.dir") + "\\Reports\\" + "ScreenShots" + "\\" + datestamp() + "\\"
				+ timestamp1() + ".jpg";
		System.out.println("Called the function and path is " + scrnshtPth);
		return scrnshtPth;
	}

	public String timestamp1() {

		String timeunitsec = new SimpleDateFormat("yyyMMddHHmmssSSS").format(new Date());
		System.out.println("Timeunit : " + timeunitsec);
		return timeunitsec;
	}

	// *******************************************************************************
	public String datestamp() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public void navigateBack() {
		driver.navigate().back();
	}

	public void killdriver() {

		Runtime runtime = Runtime.getRuntime();
		try {
			Process p1 = runtime.exec("cmd /c start C:\\SeleniumDemoProject\\KillChrome.bat");
			InputStream is = p1.getInputStream();
			int i = 0;
			while ((i = is.read()) != -1) {
				System.out.print((char) i);
			}
		} catch (IOException ioException) {
			System.out.println(ioException.getMessage());
		}

	}

	public WebDriver Browseropen(String BrowserName) throws Exception {
		try {

			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");

			Runtime.getRuntime().exec("taskkill /F /IM msedge.exe");

			if (BrowserName == null) {

			}
			browser_val = BrowserName;

			switch (browser_val) {

			case "edge":
				String iflag1 = "0";
				for (int i = 0; i <= 3; i++) {

					String edge_srvr_path = System.getProperty("user.dir") + "\\Libs\\msedgedriver.exe";

					System.setProperty("webdriver.edge.driver", edge_srvr_path);
					EdgeOptions option = new EdgeOptions();

					driver = new EdgeDriver(option);
					driver.manage().window().maximize();
					System.out.println("Launched Edge Browser");

					// test.log(LogStatus.PASS, "Launched Chrome Browser");

					iflag1 = "1";
					if (iflag1 == "1") {
						break;
					}

				}

				break;

			case "firefox":
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\libs\\Browserdrivers\\geckodriver.exe");
				FirefoxProfile myProfile = new FirefoxProfile();
				driver = new FirefoxDriver();

				driver.manage().window().maximize();
				break;
			case "chrome":
				String iflag = "0";
				for (int i = 0; i <= 3; i++) {
					// WebDriverManager.chromedriver().setup();
					String chrome_srvr_path = System.getProperty("user.dir") + "\\Libs\\chromedriver.exe";
					System.setProperty("webdriver.chrome.driver", chrome_srvr_path);
					String WINDOW_SIZE = "1920,1080";
					ChromeOptions options = new ChromeOptions();
					// options.setBinary("C:\\Program Files
					// (x86)\\Google\\Chrome\\Application\\chrome.exe");

					// Headless mode start
//			
//				options.addArguments("--no-sandbox");
//				options.addArguments("--headless");
//				options.addArguments("window-size=1200x600");
//				
					// Headless mode End

					// options.addArguments("--lang=fr"); // Language setting

					options.addArguments("--disable-notifications");
					options.addArguments("--ignore-certificate-errors");
					driver = new ChromeDriver(options);

					driver.manage().window().maximize();
					System.out.println("Launched Chrome Browser");

					iflag = "1";
					if (iflag == "1") {
						break;
					}
				}

				break;

			default:

			}

		} catch (Exception e) {
			System.out.println("Failed to Launch Browser" + e.getMessage());

		}
		return driver;

	}

	public String getTestName(Method caller) {
		Test testAnnotation = caller.getAnnotation(Test.class);
		caller.getName();
		if (testAnnotation != null) {
			return testAnnotation.testName();
		}
		return "";
	}

	public void closeCurrentBrowser() {
		driver.close();
	}

	// @AfterSuite
	public void closeDriver() throws Exception {

		// driver.manage().deleteAllCookies();
		driver.close();
		Runtime.getRuntime().exec("taskkill /IM " + browser_val + "driver.exe");

	}

	// @BeforeMethod
	public void quitDriver() throws IOException {
		driver.manage().deleteAllCookies();
		driver.quit();
		Runtime.getRuntime().exec("taskkill /IM " + browser_val + "driver.exe");

	}

//	public static void waitForPageLoad(int seconds) {
//		ExpectedCondition expectation = new ExpectedCondition() {
//			public Boolean apply(WebDriver driver) {
//				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
//			}
//
//			public Object apply(Object arg0) {
//				return null;
//			}
//		};
//		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(null)
//				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NullPointerException.class);
//		try {
//			wait.until(expectation);
//		} catch (Throwable error) {
//			//test.log(LogStatus.INFO, "Still Loading the Page!");
//			//test.log(LogStatus.FAIL, "Unable to Load Page");
//		}
//	}
//	

	public void enterValueIntoTextBox(WebElement ObjectName, String TextFieldName, String Value) throws Exception {
		try {
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			if (ObjectName.isEnabled() == true && ObjectName.isDisplayed()) {
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[1].value = arguments[0]; ", Value, ObjectName);
				Thread.sleep(1000);
				System.out.println("Entered " + Value + " into " + TextFieldName);

			} else {

				System.out.println("Entered " + Value + " not enteredn Please check " + TextFieldName);
			}

		} catch (Exception e) {

			System.out.println("Entered " + Value + " not enteredn Please check " + TextFieldName);
		}

	}

	public void enterValueIntoTextbx(WebElement ObjectName, String TextFieldName, String Value) throws Exception {
		try {
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			if (ObjectName.isEnabled() == true && ObjectName.isDisplayed()) {
				ObjectName.clear();
				ObjectName.sendKeys(Value);

			} else {

				System.out.println("Enter a value is not entered" + TextFieldName);
			}

		} catch (Exception e) {

			System.out.println("Enter a value is not entered" + TextFieldName);
		}

	}

	public void clickOnButton(WebElement ObjectName, String butttonname) throws Exception {

		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Thread.sleep(500);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ObjectName);
			if (ObjectName.isEnabled()) {
				try {
					js.executeScript("arguments[0].click();", ObjectName);
					Thread.sleep(500);
					System.out.println("Clicked on " + butttonname);
				} catch (Exception e) {

					ObjectName.click();
					Thread.sleep(500);
					System.out.println("Clicked on " + butttonname);
				}

			} else {

				System.out.println("The given button " + butttonname + " is not identified in the page");

			}

		} catch (Exception e) {

			System.out.println("The given button is not identified in the page");

		}
	}

	public void clickonElement(WebElement ObjectName, String butttonname) throws Exception {

		try {
			Thread.sleep(500);
			ObjectName.click();

			System.out.println("Clicked on " + butttonname);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("The given button is not identified in the page");

		}
	}

	public void clearTextBox(WebElement ObjectName, String butttonname) throws Exception {

		try {
			Thread.sleep(500);
			ObjectName.clear();
			System.out.println("Cleared " + butttonname);

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	public static void waitForSpecificElement(WebElement element, String elementName, Duration waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public boolean WaitForElement(WebElement element, String elementName, Duration waitTime) throws Exception {
		boolean flag = false;
		System.out.println("wait time is " + WaitTime);
		try {
			System.out.println("wait count is " + WaitCount);
			if (WaitCount == WaitTime) {
				System.out.println("Waiting Time for the element is exhausted!!! " + elementName);
				WaitCount = 0;
				flag = false;
				return flag;
			} else {
				WebDriverWait wait = new WebDriverWait(driver, waitTime);
				wait.until(ExpectedConditions.visibilityOf(element));
				wait.until(ExpectedConditions.elementToBeClickable(element));
				flag = true;
				return flag;

			}

		} catch (Exception e) {

			WaitCount++;
			WaitForElement(element, elementName, Duration.ofSeconds(10));

		}
		if (flag = false) {
			String scrnshtPth = ScreenshotPath();

		}
		return flag;
	}

	public void switchToAnotherFrame() {
		try {
			String frameID = null;
			List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
			for (WebElement iframe : iframes) {
				frameID = iframe.getAttribute("id");
				System.out.println("Frame ID is  " + iframe.getAttribute("id"));
				if (iframes.size() == 1) {
					driver.switchTo().frame(frameID);
					Thread.sleep(2000);

				} else {
					System.out.println("There is More than One Frame. Not Able to SWitch to Other Frame");
					System.out.println("More than one frame");

				}

			}
		} catch (Exception e) {
			System.out.println("Not Able to Switch to Another Frame");

		}

	}

	public void selectCalendarDate(String datetoSelect, List<WebElement> List_elmt_date) {
		try {
			String[] date_Select = datetoSelect.split("/");
			String exp_date = date_Select[0];

			int size = List_elmt_date.size();

			for (int i = 0; i <= size; i++) {
				String dateAvailbl = List_elmt_date.get(i).getText();

				if ((Integer.parseInt(exp_date)) == (Integer.parseInt(dateAvailbl))) {
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", List_elmt_date.get(i));
					i = i + size;

				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

	}

	public void selectDateFromCalendar(String datetoSelect, List<WebElement> List_elmt_date,
			WebElement cal_year_leftArrow, WebElement cal_month_leftArrow, WebElement cal_month_RightArrow,
			WebElement getText_calYear, WebElement getText_calMonth) {
		try {
			String[] exp_date_Select = datetoSelect.split("/");
			/*
			 * String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			 * System.out.println(date); String[] current_date_Select = date.split("-");
			 */

			int exp_year = Integer.parseInt(exp_date_Select[2]);
			int exp_month = Integer.parseInt(exp_date_Select[0]);
			int exp_date = Integer.parseInt(exp_date_Select[1]);

			// System.out.println(date);
			int current_year = Integer.parseInt(getText_calYear.getText());
			String current_month = getText_calMonth.getText();
			int month = 0;
			String monthString;
			monthString = current_month;
			if (monthString.trim().equalsIgnoreCase("January")) {
				month = 1;
			} else if (monthString.trim().equalsIgnoreCase("February")) {
				month = 2;
			} else if (monthString.trim().equalsIgnoreCase("March")) {
				month = 3;
			} else if (monthString.trim().equalsIgnoreCase("April")) {
				month = 4;
			} else if (monthString.trim().equalsIgnoreCase("May")) {
				month = 5;
			} else if (monthString.trim().equalsIgnoreCase("June")) {
				month = 6;
			} else if (monthString.trim().equalsIgnoreCase("July")) {
				month = 7;
			} else if (monthString.trim().equalsIgnoreCase("August")) {
				month = 8;
			} else if (monthString.trim().equalsIgnoreCase("September")) {
				month = 9;
			} else if (monthString.trim().equalsIgnoreCase("October")) {
				month = 10;
			} else if (monthString.trim().equalsIgnoreCase("November")) {
				month = 11;
			} else if (monthString.trim().equalsIgnoreCase("December")) {
				month = 12;
			} else {
				System.out.println("Invalid Month");

			}

			while (exp_year != current_year) {

				clickOnButton(cal_year_leftArrow, "Left Arrow Calendar Year");
				current_year = Integer.parseInt(getText_calYear.getText());
				if (exp_year == current_year) {
					System.out.println("Expected and Current Year is Matching ");
					{
						if (exp_month == month) {
							System.out.println("Expected and Current Month is Matching");

						} else if (exp_month > month) {
							while (exp_month != month) {
								clickOnButton(cal_month_RightArrow, "Right Arrow Cal Month");
								current_month = getText_calMonth.getText();
								monthString = current_month;
								if (monthString.trim().equalsIgnoreCase("January")) {
									month = 1;
								} else if (monthString.trim().equalsIgnoreCase("February")) {
									month = 2;
								} else if (monthString.trim().equalsIgnoreCase("March")) {
									month = 3;
								} else if (monthString.trim().equalsIgnoreCase("April")) {
									month = 4;
								} else if (monthString.trim().equalsIgnoreCase("May")) {
									month = 5;
								} else if (monthString.trim().equalsIgnoreCase("June")) {
									month = 6;
								} else if (monthString.trim().equalsIgnoreCase("July")) {
									month = 7;
								} else if (monthString.trim().equalsIgnoreCase("August")) {
									month = 8;
								} else if (monthString.trim().equalsIgnoreCase("September")) {
									month = 9;
								} else if (monthString.trim().equalsIgnoreCase("October")) {
									month = 10;
								} else if (monthString.trim().equalsIgnoreCase("November")) {
									month = 11;
								} else if (monthString.trim().equalsIgnoreCase("December")) {
									month = 12;
								} else {
									System.out.println("Invalid Month");

								}
							}

						} else if (exp_month < month) {
							while (exp_month != month) {
								clickOnButton(cal_month_leftArrow, "Left Arrow Cal Month");
								current_month = getText_calMonth.getText();
								monthString = current_month;
								if (monthString.trim().equalsIgnoreCase("January")) {
									month = 1;
								} else if (monthString.trim().equalsIgnoreCase("February")) {
									month = 2;
								} else if (monthString.trim().equalsIgnoreCase("March")) {
									month = 3;
								} else if (monthString.trim().equalsIgnoreCase("April")) {
									month = 4;
								} else if (monthString.trim().equalsIgnoreCase("May")) {
									month = 5;
								} else if (monthString.trim().equalsIgnoreCase("June")) {
									month = 6;
								} else if (monthString.trim().equalsIgnoreCase("July")) {
									month = 7;
								} else if (monthString.trim().equalsIgnoreCase("August")) {
									month = 8;
								} else if (monthString.trim().equalsIgnoreCase("September")) {
									month = 9;
								} else if (monthString.trim().equalsIgnoreCase("October")) {
									month = 10;
								} else if (monthString.trim().equalsIgnoreCase("November")) {
									month = 11;
								} else if (monthString.trim().equalsIgnoreCase("December")) {
									month = 12;
								} else {
									System.out.println("Invalid Month");

								}
							}
						}
					}
				}
			}

			int size = List_elmt_date.size();

			for (int i = 0; i <= size; i++) {
				String dateAvailbl = List_elmt_date.get(i).getText();

				if ((exp_date) == (Integer.parseInt(dateAvailbl))) {
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", List_elmt_date.get(i));
					i = i + size;

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

	}

	public void selectCalendarDueDate(String datetoSelect, List<WebElement> List_elmt_date) {
		try {
			String[] date_Select = datetoSelect.split("/");
			String exp_date = date_Select[1];

			int size = List_elmt_date.size();

			for (int i = 0; i <= size; i++) {
				String dateAvailbl = List_elmt_date.get(i).getText();

				if ((Integer.parseInt(exp_date)) == (Integer.parseInt(dateAvailbl))) {
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", List_elmt_date.get(i));
					i = i + size;

				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

	}

	public int getCurrentMonth() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		System.out.println("Current Month is : " + month);
		return month;
	}

	public int getCurrentYear() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		System.out.println("Current Year is : " + year);
		return year;
	}

	public void chooseFromDropDown(WebElement element, List<WebElement> listelement, String expDrpDwnValue)
			throws Exception {
		try {
			Actions act = new Actions(driver);
			clickonElement(element, "List of Country");
			clickonElement(listelement.get(1), "First Element in List");
			int size = listelement.size();

			for (int i = 0; i < size; i++) {
				String drpDwnValue = listelement.get(i).getText();
				System.out.println(drpDwnValue);
				scrollByElement(listelement.get(i));

				if (drpDwnValue.trim().equalsIgnoreCase(expDrpDwnValue)) {
					clickonElement(listelement.get(i), "DropDown Value");
					Thread.sleep(1000);
					break;
				}
			}

		} catch (Exception e) {
			closeDriver();
		}
	}

	public void selectFromDropDown(WebElement element, String dropdownvalue, String objectname, int index)
			throws Exception {
		try {
			Thread.sleep(2000);
			if (element.isDisplayed() && element.isEnabled()) {
				element.click();
				Thread.sleep(2000);
				Select dropDown = new Select(element);
				if (dropdownvalue.equals("")) {
					dropDown.selectByIndex(index);
					Thread.sleep(2000);
					System.out.println(dropdownvalue + " Selected from DropDown " + objectname);
				} else {
					dropDown.selectByVisibleText(dropdownvalue);
					Thread.sleep(2000);
					System.out.println(dropdownvalue + " Selected from DropDown " + objectname);
				}
			} else {

				System.out.println("Select the Drop down value (" + dropdownvalue + "Drop down  (" + objectname
						+ ") is not displayed/enabled on the page");

			}
		} catch (Exception exception) {

			System.out.println("Select the Drop down value (" + dropdownvalue + "Drop down  (" + objectname
					+ ") value is not there");

		}
	}

	public void selectValueFromDropDown(WebElement element, String dropdownvalue, String objectname, int index)
			throws Exception {
		try {
			Thread.sleep(2000);
			if (element.isDisplayed() && element.isEnabled()) {
				Select dropDown = new Select(element);
				if (dropdownvalue.equals("")) {
					dropDown.selectByIndex(index);
					Thread.sleep(1000);
					System.out.println(dropdownvalue + " Selected from DropDown " + objectname);
				} else {
					dropDown.selectByVisibleText(dropdownvalue);
					Thread.sleep(1000);
					System.out.println(dropdownvalue + " Selected from DropDown " + objectname);
				}
			} else {

				System.out.println("Select the Drop down value (" + dropdownvalue + "Drop down  (" + objectname
						+ ") is not displayed/enabled on the page");

			}
		} catch (Exception exception) {

			System.out.println("Select the Drop down value (" + dropdownvalue + "Drop down  (" + objectname
					+ ") value is not there");

		}
	}

	public String getCurrentWindowID() {
		String parent = null;
		try {
			parent = driver.getWindowHandle();

		} catch (Exception e) {
			System.out.println("Could not get Current Window ID");

		}

		return parent;
	}

	public void moveBackToCurrentWindowID(String parentWinID) {
		try {
			driver.switchTo().window(parentWinID);
			Thread.sleep(2000);

		} catch (Exception e) {
			System.out.println("Could not Switch to Current Window");

		}
	}

	public void switchToParentWindow() {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			int count = allWindows.size();
			Iterator<String> I1 = allWindows.iterator();

			while (I1.hasNext()) {
				int yy = 0;
				String parent = I1.next();
				driver.switchTo().window(parent);
				Thread.sleep(2000);
				// System.out.println(driver.switchTo().window(parent).getTitle());
			}

		} catch (Exception e) {
			System.out.println("Not Able to SWitch to Parent Window");

		}

	}

	public void switchToChildWindow() {
		try {
			String parent = driver.getWindowHandle();

			Set<String> allWindows = driver.getWindowHandles();
			int count = allWindows.size();
			Iterator<String> I1 = allWindows.iterator();

			while (I1.hasNext()) {

				String child_window = I1.next();
				if (!parent.equals(child_window)) {
					driver.switchTo().window(child_window);
					Thread.sleep(2000);
					// System.out.println(driver.switchTo().window(child_window).getTitle());
				}
			}

		} catch (Exception e) {
			System.out.println("Not Able to SWitch to Child Window");

		}

	}

	public static void waitForPageLoad() {
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);

	}

	public void verify_textFromListTableAndClick(WebElement elementToDispld, List<WebElement> listElmt_row,
			List<WebElement> listElmt_col, String verifytext) {
		try {
			if (elementToDispld.isDisplayed()) {
				for (int i = 1; i <= listElmt_row.size(); i++) {
					for (int j = 0; j <= listElmt_col.size(); j++) {
						String value = listElmt_col.get(j).getText();
						if (value.trim().equalsIgnoreCase(verifytext)) {
							listElmt_col.get(j).click();
							Thread.sleep(2000);
							break;
						}
					}
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("Could not Verify Text Or Element is not Displayed");

		}
	}

	public void verify_textFromListOfTablesAndClickElement(WebElement elementToDispld, List<WebElement> listElmt_row,
			List<WebElement> listElmt_col, String verifytext) {
		try {
			if (elementToDispld.isDisplayed()) {
				for (int i = 1; i <= listElmt_row.size(); i++) {
					for (int j = 0; j <= listElmt_col.size(); j++) {
						String value = listElmt_col.get(j).getText();
						if (value.trim().equalsIgnoreCase(verifytext)) {
							listElmt_col.get(j).click();
							Thread.sleep(2000);
							break;
						}
					}
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("Could not Verify Text Or Element is not Displayed");

		}
	}

	public String verify_textDatasFromListTable(WebElement elementToDispld, List<WebElement> listElmt_row,
			List<WebElement> listElmt_col, String verifytext, String fieldName) {
		String status = null;
		try {
			if (elementToDispld.isDisplayed()) {
				for (int i = 1; i <= listElmt_row.size(); i++) {
					int siz = listElmt_col.size();
					for (int j = 0; j <= listElmt_col.size(); j++) {
						String value = listElmt_col.get(j).getText();
						System.out.println(value);

						String[] text = verifytext.split(";");
						for (int k = 0; k < text.length; k++) {
							if (value.trim().equalsIgnoreCase(text[k])) {
								status = value;
								System.out.println(fieldName + "==>  " + value + " is Matching with the " + text[k]);
								System.out.println(fieldName + "==>  " + value + " is Matching with the " + text[k]);
								j = j + siz;
								break;
							}
						}

					}
				}
			}

		} catch (Exception e) {
			System.out.println("Could not Verify Text Or Element is not Displayed");

		}
		return status;
	}

	public void scrollByElement(WebElement element) {

		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			System.out.println("Could not Scroll the Page");

		}
	}

	public String getTextofElement(WebElement element) {
		String actualValue = null;
		try {
			actualValue = element.getText();
			Thread.sleep(500);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getMessage());
		}
		return actualValue;
	}

	public void waitforElementToBeInvisible(WebElement element, String elementName, Duration WaitTime) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, WaitTime);
			wait.until(ExpectedConditions.invisibilityOf(element));
			Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println(elementName + " is not displayed !! Proceed ");
		}

	}

	public void verify_ElementDisplayed(WebElement elementToDispld, String elementName) {
		try {
			if (elementToDispld.isDisplayed()) {
				System.out.println("Element is Displayed " + elementName + " !!");

			} else {
				System.out.println("Element is Not Displayed " + elementName + " !!");

			}

		} catch (Exception e) {
			System.out.println("Element is not Displayed");

		}
	}

	public void refreshPage() {
		try {
			driver.navigate().refresh();
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			System.out.println(e.getMessage());

		}
	}

	public void moveToElement(WebElement element) {
		Actions act = new Actions(driver);
		act.moveToElement(element).build().perform();

	}

}
