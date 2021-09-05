package ru.geekbrains.lesson3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;

public class CreateCrmExpenseRequestExample {
    public static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginToCrm();

        driver.get("https://crm.geekbrains.space/expense-request/");
        driver.findElement(By.xpath("//a[@title='Создать заявку на расход']")).click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.name("crm_expense_request[description]")));

        driver.findElement(By.name("crm_expense_request[description]")).sendKeys("Test");

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

        Thread.sleep(5000);
        driver.quit();
    }

    public static void loginToCrm() {
        driver.get("https://crm.geekbrains.space/user/login");
        driver.findElement(By.id("prependedInput")).sendKeys("Applanatest1");
        driver.findElement(By.id("prependedInput2")).sendKeys("Student2020!");
        driver.findElement(By.xpath("//button[@class='btn btn-large  btn-primary pull-right']")).click();
    }
}
