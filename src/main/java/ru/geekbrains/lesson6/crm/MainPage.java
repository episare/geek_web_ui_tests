package ru.geekbrains.lesson6.crm;

import org.openqa.selenium.WebDriver;
import ru.geekbrains.lesson6.myproject.ToolbarMenu;

public class MainPage extends BaseView{
    public NavigationMenu navigationMenu;
    public ToolbarMenu toolbarMenu;

    public MainPage(WebDriver driver) {
        super(driver);
        navigationMenu = new NavigationMenu(driver);
        toolbarMenu = new ToolbarMenu(driver);
    }
}
