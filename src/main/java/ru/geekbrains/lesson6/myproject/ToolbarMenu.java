package ru.geekbrains.lesson6.myproject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ToolbarMenu extends BaseView {
    public ToolbarMenu(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//ul[@class='toolbar_nav']/li")
    //div[@class='toolbar_nav_i_tooltip_cnt']
    List<WebElement> toolbarMenu;

    public void openToolbarItem(String itemName) {

        for (WebElement item : toolbarMenu) {
            WebElement note = item.findElement(By.xpath(".//div[@class='toolbar_nav_i_tooltip_cnt']"));
            String contents = (String) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].innerHTML;", note);
//            System.out.println("Content: " + contents);
            if (contents.equals(itemName)) {
                item.click();
            }
        }
    }
}


