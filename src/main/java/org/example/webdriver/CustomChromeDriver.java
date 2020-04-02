package org.example.webdriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CustomChromeDriver {

    private static CustomChromeDriver customChromeDriver;
    private ChromeDriver driver;
    private static Logger logger = LogManager.getLogger();

    private CustomChromeDriver() {
        logger.info("Инициализирую Chrome Driver");
        System.setProperty("webdriver.chrome.driver", "bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    public static CustomChromeDriver getInstance() {
        if (customChromeDriver == null) customChromeDriver = new CustomChromeDriver();
        return customChromeDriver;
    }

    public ChromeDriver getDriver() {
        return driver;
    }
}
