package ru.geekbrains.lesson6.crm;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.geekbrains.lesson7.CustomLogger;
import static io.qameta.allure.Allure.step;

public class BaseTest {
    //    WebDriver driver;
    EventFiringWebDriver driver;
    WebDriverWait webDriverWait;

    @BeforeAll
    static void registerDriver() {
        step("Обновляем драйвер");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void initDriver() {
        step("Инициализируем драйвер и привязываем к Allure Listener");
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.register(new CustomLogger());
        webDriverWait = new WebDriverWait(driver, 5);
    }

    @AfterEach
    void tearDown() {

        step("Выводим лог браузера");
        LogEntries browserConsoleLogs = driver.manage().logs().get("browser");
        for (LogEntry browserConsoleLog : browserConsoleLogs) {
            Allure.addAttachment("Элемент лога браузера", browserConsoleLog.getMessage());
        }

        step("Закрываем драйвер");
        driver.quit();
    }
}
