package ru.geekbrains.lesson6.myproject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static ru.geekbrains.lesson6.myproject.SideNavigateMenu.getMenuItem;

public class MainSideNavigationMenu extends BaseView{
    public MainSideNavigationMenu(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@class='nav-side __navigation __user-main']/*")
    List<WebElement> sideMenuItems;

    public WebElement getSideMenuItem(String menuItemName) throws MenuItemNotFoundException {
            return getMenuItem(sideMenuItems, menuItemName);
    }
}
