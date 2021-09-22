package ru.geekbrains.lesson5.homework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateNoteTest {
    static WebDriver driver;
    static WebDriverWait webDriverWait;
    private static final String BASE_URL = "https://ok.ru/";
    JavascriptExecutor javascriptExecutor;
    static String cookieValue;
    static int startNotesNumber = 0;

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
        loginToOk();
        setStartNumberOfHiddenNotes();
        System.out.println("startNotesNumber: " + startNotesNumber);
    }

    @BeforeEach
    void setupBrowser() throws InterruptedException {
        driver = new ChromeDriver();
        javascriptExecutor = (JavascriptExecutor) driver;
        webDriverWait = new WebDriverWait(driver, 10);
        loginToOkWithCookie();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void numberHiddenNotesTest() {
        driver.get(BASE_URL);

        driver.findElement(By
                .xpath("//a[@class='nav-side_i  __with-ic']/div[text()='Заметки']/*[name()='svg']")).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Скрытые']")));
        driver.findElement(By.xpath("//div[text()='Скрытые']")).click();

        int actualNotesNumber = getNumberOfHiddenNotes();

        assertEquals(startNotesNumber, actualNotesNumber);
    }

    @Test
    void createMyNoteTest() throws InterruptedException {
        driver.get(BASE_URL + "profile/584815941348/statuses/unpublished");

        String textNote = "Это моя " + (startNotesNumber + 1) + "-я заметка";

        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Напишите заметку']/a")));
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[text()='Напишите заметку']/a")).click();
        webDriverWait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//div[contains(@class,'ok-posting-handler')]")));
        driver.findElement(By.xpath("//div[contains(@class,'ok-posting-handler')]"))
                .sendKeys(textNote);

        driver.findElement(By.xpath("//span[@class='button-pro posting_settings-btn __sec']")).click();

        if (!driver.findElement(By.xpath("//input[@name='st.toggleComments']")).isSelected()) {
            driver.findElement(By.xpath("//span[text()='Отключить комментирование']")).click();
        }

        if (!driver.findElement(By.xpath("//input[@name='st.unpublished']")).isSelected()) {
            driver.findElement(By.xpath("//span[text()='Скрытая публикация']")).click();
        }

        driver.findElement(By.xpath("//div[text()='Сохранить']")).click();

        int actualNotesNumber = getNumberOfHiddenNotes();

        assertEquals(startNotesNumber, actualNotesNumber);

        startNotesNumber++;
    }

    public static void loginToOk() {
        WebDriver driver = new ChromeDriver();
        driver.get(BASE_URL);
        driver.findElement(By.xpath("//input[@name='st.email']")).sendKeys("lkpisare@rambler.ru");
        driver.findElement(By.xpath("//input[@name='st.password']")).sendKeys("testgb-2021");
        driver.findElement(By.xpath("//input[@class='button-pro __wide']")).click();
        Set<Cookie> cookies = driver.manage().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName());
                if (cookie.getName().equals("AUTHCODE")) {
                    cookieValue = cookie.getValue();
                }
            }
        }
        driver.quit();
    }

    void loginToOkWithCookie() throws InterruptedException {
        driver.get(BASE_URL);
        Cookie authCookie = new Cookie("AUTHCODE", cookieValue);
        driver.manage().addCookie(authCookie);
    }

    private static int getNumberOfHiddenNotes() {
        int notesNumber = 0;
        try {
            webDriverWait.until(ExpectedConditions
                    .presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@id, 'hook_Block_mediatopic')]")));
            List<WebElement> webElementList =
                    driver.findElements(By.xpath("//div[contains(@id, 'hook_Block_mediatopic')]"));
            notesNumber = webElementList.size();
        } catch (TimeoutException e) {
        }
        return notesNumber;
    }

    private static void setStartNumberOfHiddenNotes() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        driver.get(BASE_URL + "profile/584815941348/statuses/unpublished");
        int notesNumber = 0;
        try {
            webDriverWait.until(ExpectedConditions
                    .presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@id, 'hook_Block_mediatopic')]")));
            List<WebElement> webElementList =
                    driver.findElements(By.xpath("//div[contains(@id, 'hook_Block_mediatopic')]"));
            notesNumber = webElementList.size();
        } catch (TimeoutException e) {
        }
        startNotesNumber = notesNumber;
        driver.quit();
    }

}
