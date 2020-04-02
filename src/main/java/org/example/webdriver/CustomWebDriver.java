package org.example.webdriver;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CustomWebDriver {

    private static Logger logger = LogManager.getLogger();
    protected WebDriverWait wait;
    private static WebDriver webDriver;

    private static CustomWebDriver customWebDriver;

    private CustomWebDriver() {
        webDriver = CustomChromeDriver.getInstance().getDriver();
        wait = new WebDriverWait(webDriver, 8, 250);
        webDriver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
    }

    public static CustomWebDriver getInstance() {
        if (customWebDriver == null)
            customWebDriver = new CustomWebDriver();
        return customWebDriver;
    }

    public WebDriver getDriver() {
        return webDriver;
    }


    public void get(String url) {
        logger.trace(String.format("Открываю сайт по адресу '%s'", url));
        webDriver.get(url);
    }

    public void close() {
        logger.trace("Закрываю последнее открытое окно");
        webDriver.close();
    }

    public void closeAllWindows() {
        logger.trace("Закрываю все окна");
        webDriver.quit();
    }

    public WebElement waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    public WebElement findByPartialLinkText(String linkText) {
        logger.trace(String.format("Ищу элемент по тексту ссылки '%s'", linkText));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(linkText)));
    }

    public List<WebElement> findElementsByXPath(String xpath) {
        logger.trace(String.format("Ищу список элементов по локатору '%s'", xpath));
        List<WebElement> elements = null;
        try {
            elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
        } catch (Exception e) {
            createScreenshot();
            e.printStackTrace();
        }
        return elements;
    }

    public WebElement findElementByXPath(String xpath) {
        logger.trace(String.format("Ищу элемент по локатору '%s'", xpath));
        WebElement element = null;
        boolean isStaleElementReferenceException = true;

        while (isStaleElementReferenceException) {
            isStaleElementReferenceException = false;
            try {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                try {
                    element = new WebDriverWait(webDriver, 3, 500)
                            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
                } catch (TimeoutException te) {
                    ((JavascriptExecutor) webDriver).executeScript(
                            "arguments[0].scrollIntoView(true);", element);
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
                }
                element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            } catch (StaleElementReferenceException sere) {
                isStaleElementReferenceException = true;
            } catch (Exception e) {
                createScreenshot();
                e.printStackTrace();
            }
        }
        return element;
    }

    public String getTextByXpath(String xPath) {
        String price = null;
        int count = 0;
        while (count < 5) {
            try {
                price = findElementByXPath(xPath).getText();
                return price;
            } catch (StaleElementReferenceException e) {
                logger.error("StaleElementReferenceException thrown " + ++count + " times");
            }
            count++;
        }
        return price;
    }

    public void performByXpath(String xPath) {
        Actions action = new Actions(webDriver);
        WebElement element = findElementByXPath(xPath);
        action.moveToElement(element);
        action.perform();
    }

    private void createScreenshot() {
        logger.trace("Создаю скриншот ошибки");
        File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        try {
            String fileName = String.format("target/generated-test-sources/test-reports/screenshots/%s-scr.jpg",
                    new SimpleDateFormat("dd.MM_HH-mm-ss").format(new Date()));
            FileUtils.copyFile(file, new File(fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}