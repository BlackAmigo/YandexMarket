package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainPage extends BasePage{

    private static Logger logger = LogManager.getLogger();

    public MainPage open(){
        driver.get("https://market.yandex.ru");
        return this;
    }

    public MainPage clickAllCategoryButton(){
        logger.info("Кликаю по кнопке 'Все категории'");
        String xPath = "//*[@id='27903767-tab']";
        driver.findElementByXPath(xPath).click();
        return this;
    }

    public MainPage performComputerTechnologyButton(){
        logger.info("Навожу курсор на категорию 'Компьютерная техника'");
        String xPath = "//*[@id='41041837-tab']";
        driver.performByXpath(xPath);
        return this;
    }

    public void clickTabletsButton(){
        logger.info("Кликаю по кнопке 'Планшеты'");
        String xPath = "//*[@id='41041837']//*[text()='Планшеты']";
        driver.findElementByXPath(xPath).click();
    }

    public MainPage setSearchField(String query){
        logger.info(String.format("Ввожу %s в поле поиска", query));
        String xPath = "//*[@id='header-search']";
        driver.findElementByXPath(xPath).sendKeys(query);
        return this;
    }

    public MainPage clickSearchButton(){
        logger.info("Кликаю по кнопке 'Найти'");
        String xPath = "//*[@type='submit']";
        driver.findElementByXPath(xPath).click();
        return this;
    }

}
