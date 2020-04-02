package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TabletsPage extends BasePage {

    private static Logger logger = LogManager.getLogger();

    public TabletsPage clickBrandCheckbox(String brand) {
        logger.info(String.format("Кликаю по чекбоксу 'Производитель %s'", brand));
        String xPath = String.format("//*[@name='Производитель %s']/following-sibling::*", brand);
        driver.findElementByXPath(xPath).click();
        return this;
    }

    public TabletsPage clickSortByPriceButton() {
        logger.info("Кликаю по кнопке сортировать 'по цене'");
        String xPath = "//*[text()='по цене']";
        driver.findElementByXPath(xPath).click();
        return this;
    }

    public List<WebElement> getElementsResult() {
        logger.info("Получаю список найденых элементов");
        String xPath = "//*[@data-id]";
        return driver.findElementsByXPath(xPath);
    }

    public String getProductTitle(int numberInList) {
        logger.info(String.format("Получаю наименование %d продукта в списке", numberInList));
        String xPath = String.format("//*[@data-id][%d]//*[@class='n-snippet-card2__title']/a", numberInList);
        return driver.getTextByXpath(xPath);
    }

    public String getProductPrice(int numberInList) {
        logger.info(String.format("Получаю цену %d продукта в списке", numberInList));
        String xPath = String.format("//*[@data-id][%d]//*[@class='price']", numberInList);
        return driver.getTextByXpath(xPath);
    }
}
