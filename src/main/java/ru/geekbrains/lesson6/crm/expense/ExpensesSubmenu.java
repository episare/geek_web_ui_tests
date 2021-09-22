package ru.geekbrains.lesson6.crm.expense;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.geekbrains.lesson6.crm.BaseView;

public class ExpensesSubmenu extends BaseView {
    public ExpensesSubmenu(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = expensesRequestsMenuItemXpathLocator)
    public WebElement expensesRequestsMenuItem;

    public void goToExpensesRequestsPage() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(expensesRequestsMenuItemXpathLocator)));
        expensesRequestsMenuItem.click();
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By
                .xpath(ExpensesRequestsPage.createExpenseXpathLocator)));
    }

    public static final String expensesRequestsMenuItemXpathLocator = "//span[text()='Заявки на расходы']";
}
