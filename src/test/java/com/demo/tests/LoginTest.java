package com.demo.tests;

import com.demo.commonClasses.FuncitonLibrary;
import com.demo.pages.LoginPage;
import com.demo.pages.SecureAreaPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest extends FuncitonLibrary {

//    private WebDriver driver;
	private LoginPage loginPage;
	private SecureAreaPage secureAreaPage;

	@BeforeClass
	public void setUp() throws Exception {
//        System.setProperty("webdriver.chrome.driver", "C:\\SeleniumDemoProject\\Libs\\chromedriver.exe");
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
     
		Browseropen("chrome");
		  driver.get("https://the-internet.herokuapp.com/login");
		loginPage = new LoginPage(driver);
		secureAreaPage = new SecureAreaPage(driver);
	}

	@Test
	public void validLoginTest() {
		loginPage.enterUsername("tomsmith");
		loginPage.enterPassword("SuperSecretPassword!");
		loginPage.clickLogin();
		Assert.assertEquals(secureAreaPage.getHeaderText(), "Secure Area");
		Assert.assertTrue(loginPage.getFlashMessage().contains("You logged into a secure area!"));
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
