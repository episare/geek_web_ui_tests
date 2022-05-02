package ru.geekbrains.lesson6.crm.project;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import ru.geekbrains.lesson6.crm.BaseView;

import java.util.List;

public class CreateProjectPage extends BaseView {
    public CreateProjectPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "crm_project[name]")
    public WebElement projectName;

    @Step("Ввести имя проекта")
    public CreateProjectPage fillProjectName(String name) {
        projectName.sendKeys(name);
        return this;
    }

    @FindBy(xpath = "//a[@class='select2-choice select2-default']")
    public WebElement dropDownOrganisation;

    @FindBy(xpath = "//div[@id='select2-drop']//input")
    public WebElement inputPatternOrganisation;

    @FindBy(xpath = listOrganisationsToPatternXpathLocator)
    public List<WebElement> listOrganisationsToPattern;

    @Step("Выбрать организацию по паттерну")
    public CreateProjectPage selectOrganisationToPattern(String pattern) {
        dropDownOrganisation.click();
        inputPatternOrganisation.sendKeys(pattern);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(listOrganisationsToPatternXpathLocator)));
        listOrganisationsToPattern.get(0).click();
        return this;
    }

    public static final String listOrganisationsToPatternXpathLocator = "//div[@class='select2-result-label']";

    @FindBy(name = "crm_project[priority]")
    public WebElement selectPriority;

    @Step("Выбрать приоритет")
    public CreateProjectPage selectPriority(String priority) {
        new Select(selectPriority).selectByVisibleText(priority);
        return this;
    }

    @FindBy(name = "crm_project[financeSource]")
    public WebElement selectFinanceSource;

    @Step("Выбрать источник финансов")
    public CreateProjectPage selectFinanceSource(String source) {
        new Select(selectFinanceSource).selectByVisibleText(source);
        return this;
    }

    @FindBy(name = "crm_project[businessUnit]")
    public WebElement selectBusynessUnit;

    @Step("Выбрать бизнес юнит")
    public CreateProjectPage selectBusynessUnit(String unit) {
        new Select(selectBusynessUnit).selectByVisibleText(unit);
        return this;
    }

    @FindBy(name = "crm_project[curator]")
    public WebElement selectCurator;

    @Step("Выбрать куратора")
    public CreateProjectPage selectCurator(String curator) {
        new Select(selectCurator).selectByVisibleText(curator);
        return this;
    }

    @FindBy(name = "crm_project[rp]")
    public WebElement selectRp;

    @Step("Выбрать RP")
    public CreateProjectPage selectRp(String rp) {
        new Select(selectRp).selectByVisibleText(rp);
        return this;
    }

    @FindBy(name = "crm_project[manager]")
    public WebElement selectManager;

    @Step("Выбрать менеджера")
    public CreateProjectPage selectManager(String manager) {
        new Select(selectManager).selectByVisibleText(manager);
        return this;
    }

    @FindBy(xpath = "//div[@class='select2-container select2']/a")
    public WebElement dropDownContactPerson;

    @FindBy(xpath = "//div[@id='select2-drop']//input")
    public WebElement inputPatternContact;

    @Step("Выбрать контакт по паттерну")
    public CreateProjectPage selectContactToPattern(String pattern) {
        webDriverWait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//div[@class='select2-container select2']")));
        dropDownContactPerson.click();

        inputPatternContact.sendKeys(pattern);
        inputPatternContact.sendKeys(Keys.ENTER);
        return this;
    }

    @FindBy(xpath = projectSaveAndCloseButtonXpathLocator)
    public WebElement buttonSaveAndClose;

    public static final String projectSaveAndCloseButtonXpathLocator = "//button[contains(text(), 'Сохранить и закрыть')]";

    //div[@class='flash-messages-holder']
    //div[@class='alert alert-success fade in top-messages ']
    //div[@class='Message']
    @FindBy(xpath = "//*[text()='Проект сохранен']")
    public WebElement confirmSaveProjectMessage;
}
