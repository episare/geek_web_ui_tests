package ru.geekbrains.lesson5.homework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCrmProjectTest {
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
    void createNewCrmProjectTest() throws InterruptedException {
        int requestsNumberBeforeCreating;
        int requestsNumberAfterCreating;
        String[] strArray;

        navigateToMyProjects();

        webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
                .xpath("//div[@class='pagination pagination-centered']")));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//label[contains(text(),'Всего из ')]")));
        strArray = driver.findElement(By.xpath("//label[contains(text(),'Всего из ')]")).getText().split(" ");
        requestsNumberBeforeCreating = Integer.parseInt(strArray[2]);
        System.out.println("requestsNumberBeforeCreating: " + requestsNumberBeforeCreating);

        callCreateANewProject();

        fillAllMandatoryFields("test" + System.currentTimeMillis());

        driver.findElement(By.xpath("//button[contains(text(), 'Сохранить и закрыть')]")).click();

        driver.navigate().to("https://crm.geekbrains.space/project/my");
        driver.navigate().refresh();

        strArray = driver.findElement(By.xpath("//label[contains(text(),'Всего из ')]")).getText().split(" ");
        requestsNumberAfterCreating = Integer.parseInt(strArray[2]);
        System.out.println("requestsNumberAfterCreating: " + requestsNumberAfterCreating);

        assertEquals(requestsNumberBeforeCreating + 1, requestsNumberAfterCreating);
    }

    @Test
    void denyCreateWhenProjectNameAlreadyExists() throws InterruptedException {

        //navigateToMyProjects();
        driver.get(BASE_URL + "project/");

        callCreateANewProject();

        fillAllMandatoryFields("test");

        driver.findElement(By.xpath("//button[contains(text(), 'Применить')]")).click();
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='validation-failed']")));

        assertEquals("Это значение уже используется."
                , driver.findElement(By.xpath("//span[@class='validation-failed']")).getText());

        Thread.sleep(2000);
    }

    @Test
    void findContactPersonWhenPatternOrganisationIs123Test() throws InterruptedException {

        //navigateToMyProjects();

        //callCreateANewProject();
        driver.get(BASE_URL + "project/create/");

        selectOrganisation("123");
        selectContactPerson("11");
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By
                .xpath("//div[contains(@id,'s2id_crm_project_contactMain')]")));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//div[contains(@id,'s2id_crm_project_contactMain')]")));
        assertEquals("Name111 Name333"
                , driver.findElement(By.xpath("//div[contains(@id,'s2id_crm_project_contactMain')]")).getText());
        Thread.sleep(2000);
    }

    @Test
    void findContactPersonWhenPatternOrganisationIsGeekBrains() throws InterruptedException {

        //navigateToMyProjects();

        //callCreateANewProject();
        driver.get(BASE_URL + "project/create/");

        selectOrganisation("GeekBrains");
        selectContactPerson("");
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By
                .xpath("//div[contains(@id,'s2id_crm_project_contactMain')]")));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//div[contains(@id,'s2id_crm_project_contactMain')]")));
        assertEquals(driver.findElement(By.xpath("//div[contains(@id,'s2id_crm_project_contactMain')]")).getText()
                , "Owl Watzlaw");
        Thread.sleep(2000);

    }

    @Test
    void textInFrameBoldItalicUnderlineTest() throws InterruptedException {
        String textForTest = "It is my text for test.";

        //navigateToMyProjects();

        //callCreateANewProject();
        driver.get(BASE_URL + "project/create/");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//iframe[contains(@id, 'crm_project_planning')]")));
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id, 'crm_project_planning')]")));
        driver.findElement(By.xpath("//body")).sendKeys(textForTest);
        WebElement textInFrame = driver.findElement(By.xpath("//body"));
        assertEquals(textForTest, textInFrame.getText());
        driver.switchTo().parentFrame();

    }

    void navigateToMyProjects() {

        driver.get(BASE_URL);

        Actions actions = new Actions(driver);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By
                .xpath("//a/span[text()='Проекты']")));
        WebElement projectMenuElement = driver.findElement(By.xpath("//a/span[text()='Проекты']"));
        actions.moveToElement(projectMenuElement).perform();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a/span[text()='Мои проекты']")));
        driver.findElement(By.xpath("//a/span[text()='Мои проекты']")).click();
    }

    void callCreateANewProject() {

        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Создать проект']")));
        driver.findElement(By.xpath("//a[text()='Создать проект']")).click();
    }

    void fillAllMandatoryFields(String projectName) {

        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.name("crm_project[name]")));
        driver.findElement(By.name("crm_project[name]")).sendKeys(projectName);

        selectOrganisation("123test");

        selectContactPerson("123");

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
    }

    void selectOrganisation(String pattern) {

        webDriverWait.until(ExpectedConditions.elementToBeClickable(By
                .xpath("//a[@class='select2-choice select2-default']")));
        driver.findElement(By.xpath("//a[@class='select2-choice select2-default']")).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='select2-drop']//input")));
        driver.findElement(
                By.xpath("//div[@id='select2-drop']//input")).sendKeys(pattern);

        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='select2-result-label']")));
        List<WebElement> organisationsVars = driver.findElements(By.xpath("//div[@class='select2-result-label']"));
        organisationsVars.get(0).click();
    }

    void selectContactPerson(String pattern) {

        webDriverWait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//div[@class='select2-container select2']")));
        driver.findElement(By.xpath("//div[contains(@id,'s2id_crm_project_contactMain')]/a")).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='select2-drop']//input")));
        driver.findElement(By.xpath("//div[@id='select2-drop']//input")).sendKeys(pattern);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By
                .xpath("//select[@name='crm_project[contactMain]']")));
        driver.findElement(By
                .xpath("//select[@name='crm_project[contactMain]']")).sendKeys(Keys.DOWN, Keys.DOWN, Keys.ENTER);
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
                System.out.println(cookie.getName());
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
