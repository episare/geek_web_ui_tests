package ru.geekbrains.lesson6.crm.project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.geekbrains.lesson6.crm.BaseView;

public class ProjectPage extends BaseView {
    public ProjectPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = createProjectXpathLocator)
    public WebElement createProjectButton;

    public CreateProjectPage createProject() {
        createProjectButton.click();
        webDriverWait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath(CreateProjectPage.projectSaveAndCloseButtonXpathLocator)));
        return new CreateProjectPage(driver);
    }

    public static final String createProjectXpathLocator = "//a[text()='Создать проект']";
}
