package org.example;

import org.example.entity.Product;
import org.example.pages.MainPage;
import org.example.pages.TabletsPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
     Шаги теста:
     1. Перейти на https://market.yandex.ru
     2. Выбрать категорию: Компьютерная техника -> Компьютеры -> Планшеты
     3. Выбрать производителя: Samsung
     4. Установить сортировку: по цене
     5. Вывести в лог первые 5 найденных товаров (если меньше 5, то все доступные товары)
     6. Запомнить вторую позицию из списка продукт (наименование и цена)
     7. Ввести в строке поиска запомненный продукт (наименование) и нажать «Поиск»
     8. Убедиться, что в выведенном списке 1 строкой указан искомый продукт по наименованию и стоимости.
     При не совпадении тест должен завершиться с ошибкой.
 */

public class T0001 {

    private static MainPage mainPage;
    private static TabletsPage tabletsPage;
    private List<Product> productList = new ArrayList<>(5);

    @BeforeClass
    public static void before() {
        mainPage = new MainPage();
        tabletsPage = new TabletsPage();
    }

    @Test
    public void test() {
        mainPage.open()
                .clickAllCategoryButton()
                .performComputerTechnologyButton()
                .clickTabletsButton();
        tabletsPage.clickBrandCheckbox("Samsung")
                .clickSortByPriceButton();

        int listSize = tabletsPage.getElementsResult().size();
        for (int i = 0; i < listSize && i < 5; i++) {
            String title = tabletsPage.getProductTitle(i + 1);
            String price = tabletsPage.getProductPrice(i + 1);
            productList.add(new Product(title, price));
        }

        productList.stream().forEach(System.out::println);

        Product product = productList.get(1);

        mainPage.setSearchField(product.getTitle());
        mainPage.clickSearchButton();

        String title = tabletsPage.getProductTitle(1);
        String price = tabletsPage.getProductPrice(1);

        assertEquals(title, product.getTitle());
        assertEquals(price, product.getPrice());
    }

    @AfterClass
    public static void after(){
        mainPage.closeAllWindows();
    }
}
