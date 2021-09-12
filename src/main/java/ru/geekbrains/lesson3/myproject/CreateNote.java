package ru.geekbrains.lesson3.myproject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CreateNote {
    public static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        driver = new ChromeDriver(chromeOptions);

        driver.get("https://ok.ru");
        loginToOk();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        driver.findElement(By
                .xpath("//a[@class='nav-side_i  __with-ic']/div[text()='Заметки']/*[name()='svg']")).click();

        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Скрытые']")));
        driver.findElement(By.xpath("//div[text()='Скрытые']")).click();

        int sizeListOfWebElements = 0;

        try {
            webDriverWait.until(ExpectedConditions
                    .presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@id, 'hook_Block_mediatopic')]")));
            List<WebElement> webElementList =
                    driver.findElements(By.xpath("//div[contains(@id, 'hook_Block_mediatopic')]"));
            sizeListOfWebElements = webElementList.size();
        } catch (TimeoutException e) {
        }
//        System.out.println(sizeListOfWebElements);
//        Thread.sleep(1000);

        String textNote;
        for (int i = 0; i < 5; i++) {
            textNote = "Это моя " + (sizeListOfWebElements + i + 1) + "-я заметка";
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Напишите заметку']/a")));
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[text()='Напишите заметку']/a")).click();
            webDriverWait.until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//div[contains(@class,'ok-posting-handler')]")));
            driver.findElement(By.xpath("//div[contains(@class,'ok-posting-handler')]"))
                    .sendKeys(textNote);
            driver.findElement(By.xpath("//div[contains(@class, 'posting_cp_i __more __highlight')]")).click();

            String xpathOfBackground = "//div[@data-uid='" + (26 + sizeListOfWebElements + i) + "']";
            webDriverWait.until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath(xpathOfBackground)));
            driver.findElement(By.xpath(xpathOfBackground)).click();
            driver.findElement(By.xpath("//div[contains(@class, 'posting_cp_i __more __highlight')]")).click();

            driver.findElement(By.xpath("//span[@class='button-pro posting_settings-btn __sec']")).click();

            if (!driver.findElement(By.xpath("//input[@name='st.toggleComments']")).isSelected()) {
                driver.findElement(By.xpath("//span[text()='Отключить комментирование']")).click();
            }

            if (!driver.findElement(By.xpath("//input[@name='st.unpublished']")).isSelected()) {
                driver.findElement(By.xpath("//span[text()='Скрытая публикация']")).click();
            }

            driver.findElement(By.xpath("//div[text()='Сохранить']")).click();

        }

        Thread.sleep(2000);

        logoffFromOk(webDriverWait);

        driver.quit();
    }

    public static void loginToOk() {
        driver.findElement(By.xpath("//input[@name='st.email']")).sendKeys("lkpisare@rambler.ru");
        driver.findElement(By.xpath("//input[@name='st.password']")).sendKeys("testgb-2021");
        driver.findElement(By.xpath("//input[@class='button-pro __wide']")).click();
    }

    public static void logoffFromOk(WebDriverWait webDriverWait) {
        driver.findElement(By.xpath("//div[@class='ucard-mini_cnt']")).click();

        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Выйти']")));
        driver.findElement(By.xpath("//a[text()='Выйти']")).click();

        webDriverWait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//input[@id='hook_FormButton_logoff.confirm_not_decorate']")));
        driver.findElement(By.xpath("//input[@id='hook_FormButton_logoff.confirm_not_decorate']")).click();
    }

    public static int getNumberOfHiddenNotes(WebDriverWait webDriverWait) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Скрытые']")));
        driver.findElement(By.xpath("//div[text()='Скрытые']")).click();

        webDriverWait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@id, 'hook_Block_mediatopic')]")));
        List<WebElement> webElementList = driver.findElements(By.xpath("//div[contains(@id, 'hook_Block_mediatopic')]"));
        return webElementList.size();
    }
}
