package ru.geekbrains.lesson6.myproject;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import java.util.List;

class SideNavigateMenu {

    @Step("Открываем боковое меню")
    public static WebElement getMenuItem(List<WebElement> itemList, String menuItemName) {
        for (WebElement item : itemList) {
            if (item.getText().equals(menuItemName)) {
                return item;
            }
        }
        throw new MenuItemNotFoundException("Пункт меню '" + menuItemName + "'не найден!");
    }
}
