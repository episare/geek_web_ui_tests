package ru.geekbrains.lesson6.myproject;

import org.openqa.selenium.WebDriver;

public class MainPage extends BaseView {
    public MainPage(WebDriver driver) {
        super(driver);
    }

    MainSideNavigationMenu mainSideNavigationMenu = new MainSideNavigationMenu(driver);
    ToolbarMenu toolbarMenu = new ToolbarMenu(driver);
    PortletSideNavigationMenu portletSideNavigationMenu = new PortletSideNavigationMenu(driver);

}
