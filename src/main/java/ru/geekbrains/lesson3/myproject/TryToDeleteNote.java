package ru.geekbrains.lesson3.myproject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TryToDeleteNote {
    public static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        driver = new ChromeDriver(chromeOptions);

        driver.get("https://ok.ru");
        loginToOk();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);

        driver.findElement(By
                .xpath("//a[@class='nav-side_i  __with-ic']/div[text()='Заметки']/*[name()='svg']")).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Скрытые']")));
        driver.findElement(By.xpath("//div[text()='Скрытые']")).click();

        webDriverWait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@id, 'hook_Block_mediatopic')]")));
        List<WebElement> webElementList = driver.findElements(By.xpath("//div[contains(@id, 'hook_Block_mediatopic')]"));
//        System.out.println("Notes number: " + webElementList.size());

        Actions actions = new Actions(driver);
        for (int i = 0; i < webElementList.size(); i++) {
            WebElement webElement = webElementList.get(i);
//            System.out.println(
//                    webElement.findElement(By.xpath(".//div[@class='media-text_cnt_tx emoji-tx textWrap']")).getText());
            actions.moveToElement(webElement).perform();
            WebElement dropMenuElement = webElement.findElement(By
                    .xpath(".//div[@class='feed_menu_ic']//span[@class='new_topic_icodown']/*[name()='svg']"));
            actions.moveToElement(dropMenuElement).perform();
            WebElement deleteHiddenElement = driver.findElement(By
                    .xpath(".//div[@class='feed_menu_ic']//div[contains(@id,'block_ShortcutMenu')]//li[4]//a/span"));


            // deleteHiddenElement.click();
            // Exception in thread "main" org.openqa.selenium.ElementNotInteractableException: element not interactable
            Thread.sleep(2000);

        }
        driver.findElement(By.id("scrollToTop")).click();
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

}