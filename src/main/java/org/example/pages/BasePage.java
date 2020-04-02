package org.example.pages;

import org.example.webdriver.CustomWebDriver;

public abstract class BasePage {

    protected CustomWebDriver driver = CustomWebDriver.getInstance();

    public void closeAllWindows(){
        driver.closeAllWindows();
    }
}
