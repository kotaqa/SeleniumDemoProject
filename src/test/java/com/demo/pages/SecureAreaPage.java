package com.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SecureAreaPage {
    private WebDriver driver;
    private By header = By.cssSelector("div.example h2");
    private By logoutButton = By.cssSelector("a.button");

    public SecureAreaPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getHeaderText() {
        return driver.findElement(header).getText();
    }

    public void clickLogout() {
        driver.findElement(logoutButton).click();
    }
}
