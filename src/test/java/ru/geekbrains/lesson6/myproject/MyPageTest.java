package ru.geekbrains.lesson6.myproject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@DisplayName("ОК.РУ - Тест создания скрытой заметки")
public class MyPageTest extends BaseTest {
    public static final String BASE_URL = "https://ok.ru";

    @Test
    void createNoteTest() throws InterruptedException {
        driver.get(BASE_URL);
        new LoginPage(driver)
                .fillLogin("lkpisare@rambler.ru")
                .fillPassword("testgb-2021")
                .loginButton.click();

        step("Выбираем в ToolbarMenu раздел \"Друзья\"");
        new ToolbarMenu(driver).openToolbarItem("Друзья");
        Thread.sleep(2000);

        driver.get(BASE_URL);

        step("Выбираем в боковом меню раздел \"Заметки\"");
        new MainSideNavigationMenu(driver).getSideMenuItem("Заметки").click();
        Thread.sleep(2000);

        step("Выбираем в PortletMenu раздел \"Скрытые\"");
        new PortletSideNavigationMenu(driver).getPortletMenuItem("Скрытые").click();
        Thread.sleep(2000);
    }
}
