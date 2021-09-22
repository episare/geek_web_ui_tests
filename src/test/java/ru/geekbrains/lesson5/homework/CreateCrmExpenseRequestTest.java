package ru.geekbrains.lesson5.homework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCrmExpenseRequestTest {
    static WebDriver driver;
    WebDriverWait webDriverWait;
    private static final String BASE_URL = "https://crm.geekbrains.space/";
    JavascriptExecutor javascriptExecutor;
    static String cookieValue;

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
        loginToCrm();
    }

    @BeforeEach
    void setupBrowser() throws InterruptedException {
        driver = new ChromeDriver();
        javascriptExecutor = (JavascriptExecutor) driver;
        webDriverWait = new WebDriverWait(driver, 10);
        loginToCrmWithCookie();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void sideRequestExpenseWillBeOpenedTest() {
        driver.get(BASE_URL + "expense-request/");
        driver.findElement(By.xpath("//a[@title='Создать заявку на расход']")).click();
        assertEquals("https://crm.geekbrains.space/expense-request/create", driver.getCurrentUrl());
    }

    @Test
    void textAreaBorderColorIsRedWhenEmptyAndInactiveTest() throws InterruptedException {
        driver.get(BASE_URL + "expense-request/create");
        driver.findElement(By.name("crm_expense_request[businessUnit]")).click();
        Thread.sleep(500);
        assertThat(driver.findElement(By.xpath("//textarea")).getCssValue("border-bottom-color"),
                is("rgba(233, 50, 45, 1)"));
    }

    @Test
    void textAreaBorderColorWhenFilledUpAndInactiveTest() throws InterruptedException {
        driver.get(BASE_URL + "expense-request/create");
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.name("crm_expense_request[description]")));
        driver.findElement(By.name("crm_expense_request[description]")).sendKeys("Test");
        driver.findElement(By.name("crm_expense_request[businessUnit]")).click();
        Thread.sleep(500);
        assertThat(driver.findElement(By.xpath("//textarea")).getCssValue("border-bottom-color"),
                is("rgba(221, 221, 221, 1)"));
    }

    @Test
    void showCalendarIfDateExecuteUntilIsSelectedTest() throws InterruptedException {
        driver.get(BASE_URL + "expense-request/create");
        driver.findElement(
                By.xpath("//input[contains(@id, 'date_selector_crm_expense_request_dateExecuteUntil')]")).click();
        assertEquals("block", driver.findElement(By.xpath("//div[@id='ui-datepicker-div']")).getCssValue("display"));
        //Thread.sleep(2000);
    }

    @Test
    void showCalendarIfDatePlanIsSelectedTest() throws InterruptedException {
        driver.get(BASE_URL + "expense-request/create");
        driver.findElement(
                By.xpath("//input[contains(@id, 'date_selector_crm_expense_request_datePlan')]")).click();
        assertEquals("block", driver.findElement(By.xpath("//div[@id='ui-datepicker-div']")).getCssValue("display"));
        //Thread.sleep(2000);
    }

    @Test
    void errorMsgBySaveWhenAllFieldsAreEmpty() throws InterruptedException {
        driver.get(BASE_URL + "expense-request/create");
        driver.findElement(By.xpath("//button[contains(text(), 'Сохранить и закрыть')]")).click();

        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='flash-messages-holder']")));
        webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='message']")));

        List<WebElement> listErrorMessages = driver
                .findElements(By.xpath("//div[@class='message']"));
        WebElement errMsgHolder = driver.findElement(By.xpath("//div[@class='flash-messages-holder']"));
        //Thread.sleep(5000);
        assertAll(
                () -> assertEquals("block", errMsgHolder.getCssValue("display")),
                () -> assertEquals(5, listErrorMessages.size()),
                () -> assertEquals("Назначение*: Значение не должно быть пустым.", listErrorMessages.get(0).getText()),
                () -> assertEquals("Подразделение : Значение не должно быть пустым.", listErrorMessages.get(1).getText()),
                () -> assertEquals("Статья расхода : Значение не должно быть пустым.", listErrorMessages.get(2).getText()),
                () -> assertEquals("Запрашиваемая сумма*: Значение не должно быть пустым.", listErrorMessages.get(3).getText()),
                () -> assertEquals("Планируемая дата*: Значение не должно быть пустым.", listErrorMessages.get(4).getText())
        );
    }

    @Test
    void createNewCrmExpenseRequestTest() {
        int requestsNumberBeforeCreating;
        int requestsNumberAfterCreating;
        String[] strArray;

        driver.get(BASE_URL + "expense-request/");

        webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
                .xpath("//div[@class='pagination pagination-centered']")));
        strArray = driver.findElement(By.xpath("//label[contains(text(),'Всего из ')]")).getText().split(" ");
        requestsNumberBeforeCreating = Integer.parseInt(strArray[2]);
        System.out.println("requestsNumberBeforeCreating: " + requestsNumberBeforeCreating);

        driver.findElement(By.xpath("//a[@title='Создать заявку на расход']")).click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.name("crm_expense_request[description]")));

        driver.findElement(By.name("crm_expense_request[description]"))
                .sendKeys("20-000" + (requestsNumberBeforeCreating + 1) + "T");

        Select businesUnitSelect = new Select(driver.findElement(By.name("crm_expense_request[businessUnit]")));
        businesUnitSelect.selectByVisibleText("Research & Development");

        Select expenditureSelect = new Select(driver.findElement(By.name("crm_expense_request[expenditure]")));
        expenditureSelect.selectByVisibleText("01103 - ОС: мебель");

        Select currencySelect = new Select(driver.findElement(By.name("crm_expense_request[currency]")));
        currencySelect.selectByVisibleText("Евро");

        driver.findElement(By.name("crm_expense_request[sumPlan]")).sendKeys("300");

        Select typeExpenseSelect = new Select(driver.findElement(By.name("crm_expense_request[type]")));
        typeExpenseSelect.selectByVisibleText("Безналичный");

        driver.findElement(
                By.xpath("//input[contains(@id, 'date_selector_crm_expense_request_dateExecuteUntil')]")).click();
        driver.findElement(By.xpath("//a[text()='25']")).click();

        driver.findElement(
                By.xpath("//input[contains(@id, 'date_selector_crm_expense_request_datePlan')]")).click();
        driver.findElement(By.xpath("//a[text()='10']")).click();

        Select subcontractSelect = new Select(driver.findElement(By.name("crm_expense_request[subcontract]")));
        subcontractSelect.selectByVisibleText("№23455");

        Select ndsSelect = new Select(driver.findElement(By.name("crm_expense_request[nds]")));
        ndsSelect.selectByVisibleText("НДС (20%)");

        driver.findElement(By.xpath("//button[contains(text(), 'Сохранить и закрыть')]")).click();

        webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
                .xpath("//div[@class='pagination pagination-centered']")));
        strArray = driver.findElement(By.xpath("//label[contains(text(),'Всего из ')]")).getText().split(" ");
        requestsNumberAfterCreating = Integer.parseInt(strArray[2]);
        System.out.println("requestsNumberAfterCreating: " + requestsNumberAfterCreating);

        assertEquals(requestsNumberBeforeCreating + 1, requestsNumberAfterCreating);

    }

    public static void loginToCrm() {
        WebDriver driver = new ChromeDriver();
        driver.get(BASE_URL + "user/login");
        driver.findElement(By.id("prependedInput")).sendKeys("Applanatest1");
        driver.findElement(By.id("prependedInput2")).sendKeys("Student2020!");
        driver.findElement(By.xpath("//button[@class='btn btn-large  btn-primary pull-right']")).click();
        Set<Cookie> cookies = driver.manage().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("BAPID")) {
                    cookieValue = cookie.getValue();
                }
            }
        }
        driver.quit();
    }

    void loginToCrmWithCookie() throws InterruptedException {
        driver.get(BASE_URL + "user/login");
        Cookie authCookie = new Cookie("BAPID", cookieValue);
        driver.manage().addCookie(authCookie);
    }

}
