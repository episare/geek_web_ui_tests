package ru.geekbrains.lesson6.crm.project;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.geekbrains.lesson6.crm.BaseView;

public class ProjectSubmenu extends BaseView {
    public ProjectSubmenu(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = projectMenuItemXpathLocator)
    public WebElement projectMenuItem;

    @Step("Перейти на страницу создания проекта")
    public void goToProjectPage() {
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(projectMenuItemXpathLocator)));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(projectMenuItemXpathLocator)));
        projectMenuItem.click();
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By
                .xpath(ProjectPage.createProjectXpathLocator)));
    }

    public static final String projectMenuItemXpathLocator = "//span[text()='Мои проекты']";
}
