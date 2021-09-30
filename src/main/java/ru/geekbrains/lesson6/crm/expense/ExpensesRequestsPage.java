package ru.geekbrains.lesson6.crm.expense;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.geekbrains.lesson6.crm.BaseView;

public class ExpensesRequestsPage extends BaseView {
    public ExpensesRequestsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = createExpenseXpathLocator)
    public WebElement createExpenseButton;

    @Step("Создать заявку на расход")
    public CreateExpensePage createExpense() {
        createExpenseButton.click();
        webDriverWait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath(CreateExpensePage.expenseSaveAndCloseButtonXpathLocator)));
        return new CreateExpensePage(driver);
    }

    public static final String createExpenseXpathLocator = "//a[text()='Создать заявку на расход']";
}
