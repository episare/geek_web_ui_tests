package ru.geekbrains.lesson6.crm.expense;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import ru.geekbrains.lesson6.crm.BaseView;

import java.util.List;

public class CreateExpensePage extends BaseView {
    public CreateExpensePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "crm_expense_request[description]")
    public WebElement expenseRequestName;

    @Step("Заполнить имя")
    public CreateExpensePage fillName(String name) {
        expenseRequestName.sendKeys(name);
        return this;
    }

    @FindBy(name = "crm_expense_request[businessUnit]")
    public WebElement selectBusinessUnit;

    @Step("Выбрать бизнесс юнит")
    public CreateExpensePage selectBusinessUnit(String businessUnit) {
        new Select(selectBusinessUnit).selectByVisibleText(businessUnit);
        return this;
    }

    @FindBy(name = "crm_expense_request[expenditure]")
    public WebElement selectExpenditure;

    @Step("Выбрать статью расходов")
    public CreateExpensePage selectExpenditure(String expenditure) {
        new Select(selectExpenditure).selectByVisibleText(expenditure);
        return this;
    }

    @FindBy(name = "crm_expense_request[currency]")
    public WebElement selectCurrency;

    @Step("Выбрать валюту")
    public CreateExpensePage selectCurrency(String currency) {
        new Select(selectCurrency).selectByVisibleText(currency);
        return this;
    }

    @FindBy(name = "crm_expense_request[sumPlan]")
    public WebElement sumPlan;

    @Step("Заполнить сумму")
    public CreateExpensePage fillSumPlan(String sum) {
        sumPlan.sendKeys(sum);
        return this;
    }

    @FindBy(name = "crm_expense_request[type]")
    public WebElement selectType;

    @Step("Выбрать тип расхода")
    public CreateExpensePage selectType(String type) {
        new Select(selectType).selectByVisibleText(type);
        return this;
    }

    @FindBy(xpath = "//input[contains(@id, 'date_selector_crm_expense_request_dateExecuteUntil')]")
    public WebElement untilExecuteDate;

    @FindBy(xpath = "//td[@data-handler='selectDay']/a")
    List<WebElement> dayInCalenderForExecuteUntil;

    @Step("Выбрать дату до осуществления расхода")
    public CreateExpensePage selectDateUntilExecute(String dayOfMonth) {
        untilExecuteDate.click();
        dayInCalenderForExecuteUntil.stream()
                .filter(element -> element.getText().equals(dayOfMonth))
                .findFirst().get().click();
        return this;
    }

    @FindBy(xpath = "//input[contains(@id, 'date_selector_crm_expense_request_datePlan')]")
    public WebElement plannedDate;

    @FindBy(xpath = "//td[@data-handler='selectDay']/a")
    List<WebElement> dayInCalenderForPlan;

    @Step("Выбрать планируемую дату")
    public CreateExpensePage selectDatePlan(String dayOfMonth) {
        plannedDate.click();
        dayInCalenderForPlan.stream()
                .filter(element -> element.getText().equals(dayOfMonth))
                .findFirst().get().click();
        return this;
    }

    @FindBy(name = "crm_expense_request[subcontract]")
    public WebElement selectSubcontract;

    @Step("Выбрать субподрядный договор")
    public CreateExpensePage selectSubcontract(String subcontract) {
        new Select(selectSubcontract).selectByVisibleText(subcontract);
        return this;
    }

    @FindBy(name = "crm_expense_request[nds]")
    public WebElement selectNds;

    @Step("Выбрать НДС")
    public CreateExpensePage selectNds(String nds) {
        new Select(selectNds).selectByVisibleText(nds);
        return this;
    }

    @FindBy(xpath = expenseSaveAndCloseButtonXpathLocator)
    public WebElement expenseSaveAndCloseButton;

    public static final String expenseSaveAndCloseButtonXpathLocator = "//button[contains(text(), 'Сохранить и закрыть')]";

    @FindBy(xpath = "//*[text()='Заявка на расход сохранена']")
    public WebElement confirmSavedSuccessfullyElement;

}
