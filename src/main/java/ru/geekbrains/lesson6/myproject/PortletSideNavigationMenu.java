package ru.geekbrains.lesson6.myproject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static ru.geekbrains.lesson6.myproject.SideNavigateMenu.getMenuItem;

public class PortletSideNavigationMenu extends BaseView{
    public PortletSideNavigationMenu(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = portletSideMenuItemsXpathLocator)
    List<WebElement> portletSideMenuItems;

    @Step("Открываем Potlet меню")
    public WebElement getPortletMenuItem(String menuItemName) throws MenuItemNotFoundException, InterruptedException {
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(portletSideMenuItemsXpathLocator)));
        return getMenuItem(portletSideMenuItems, menuItemName);
    }

    public static final String portletSideMenuItemsXpathLocator = "//div[@class='nav-side ']/*";
}

