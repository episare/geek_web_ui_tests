package ru.geekbrains.lesson3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CreateCrmProjectExample {
    public static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();

//        scenarioWithExtention();

        driver = new ChromeDriver();
//        runJsScriptExample();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        loginToCrm();

        Actions actions = new Actions(driver);
        WebElement projectMenuElement = driver.findElement(By.xpath("//a/span[text()='Проекты']"));
        actions.moveToElement(projectMenuElement).perform();
        driver.findElement(By.xpath("//a/span[text()='Мои проекты']")).click();

        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Создать проект']")));
        driver.findElement(By.xpath("//a[text()='Создать проект']")).click();

        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.name("crm_project[name]")));
        driver.findElement(By.name("crm_project[name]")).sendKeys("test"+System.currentTimeMillis());

        driver.findElement(By.xpath("//a[@class='select2-choice select2-default']")).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='select2-drop']//input")));
        driver.findElement(
                By.xpath("//div[@id='select2-drop']//input")).sendKeys("test");

        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='select2-result-label']")));
        List<WebElement> organisationsVars = driver.findElements(By.xpath("//div[@class='select2-result-label']"));
        organisationsVars.get(0).click();

        Select prioritySelect = new Select(driver.findElement(By.name("crm_project[priority]")));
        prioritySelect.selectByVisibleText("Низкий");

        Select financeSourceSelect = new Select(driver.findElement(By.name("crm_project[financeSource]")));
        financeSourceSelect.selectByVisibleText("Внутреннее");

        Select businessUnitSelect = new Select(driver.findElement(By.name("crm_project[businessUnit]")));
        businessUnitSelect.selectByVisibleText("Research & Development");

        Select curatorSelect = new Select(driver.findElement(By.name("crm_project[curator]")));
        curatorSelect.selectByVisibleText("Applanatest Applanatest Applanatest");

        Select rpSelect = new Select(driver.findElement(By.name("crm_project[rp]")));
        rpSelect.selectByVisibleText("Applanatest2 Applanatest2 Applanatest2");

        Select managerSelect = new Select(driver.findElement(By.name("crm_project[manager]")));
        managerSelect.selectByVisibleText("Applanatest1 Applanatest1 Applanatest1");

        webDriverWait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//div[@class='select2-container select2']")));
        driver.findElement(By.xpath("//div[contains(@id,'s2id_crm_project_contactMain')]/a")).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='select2-drop']//input")));
        driver.findElement(By.xpath("//div[@id='select2-drop']//input")).sendKeys("1111");
        driver.findElement(By.xpath("//div[@id='select2-drop']//input")).sendKeys(Keys.ENTER);

        Select reportIntervalSelect = new Select(driver.findElement(By.name("crm_project[reportInterval]")));
        reportIntervalSelect.selectByVisibleText("Не формировать");

        driver.findElement(By
                        .xpath("//label[text()='Планирование']/../following-sibling::div//i[@class='mce-ico mce-i-bold']"))
                .click();
        driver.findElement(By
                        .xpath("//label[text()='Планирование']/../following-sibling::div//i[@class='mce-ico mce-i-italic']"))
                .click();
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id, 'crm_project_planning')]")));
        driver.findElement(By.xpath("//body")).sendKeys("Text bold italic");

        driver.switchTo().parentFrame();

        driver.findElement(By
                        .xpath("//label[text()='Управление требованиями']/../following-sibling::div//i[@class='mce-ico mce-i-underline']"))
                .click();
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id, 'crm_project_requirementsManagement')]")));
        driver.findElement(By.xpath("//body")).sendKeys("Text underline");

        driver.switchTo().parentFrame();

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

    public static void scenarioWithExtention () throws InterruptedException {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("user-data-dir=D:/_GeekBrains/Test automation Web UI/geek_web_ui_tests/src/main/resources/chrome_profile");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://afisha.ru");
        Thread.sleep(10000);
    }

    public static void runJsScriptExample () throws InterruptedException {
        driver.get("https://afisha.ru");
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

        javascriptExecutor.executeScript("function getElementByXpath(path) {\n" +
                "         return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\n" +
                "      }\n" +
                "      \n" +
                "      getElementByXpath(\"//div[@data-test='HONEY-AD AD-ad_1']\").remove();");

        Thread.sleep(10000);

        // Script для удаления элемента на странице по Xpath
        // function getElementByXpath(path) {
        //         return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
        //      }
        //      getElementByXpath("//div[@data-test='HONEY-AD AD-ad_1']").remove();

    }
}
