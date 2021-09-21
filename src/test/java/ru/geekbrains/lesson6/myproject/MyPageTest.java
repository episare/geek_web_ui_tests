package ru.geekbrains.lesson6.myproject;

import org.junit.jupiter.api.Test;

public class MyPageTest extends BaseTest{
    public static final String BASE_URL = "https://ok.ru";

    @Test
    void createNoteTest() throws InterruptedException {
        driver.get(BASE_URL);
        new LoginPage(driver)
                .fillLogin("lkpisare@rambler.ru")
                .fillPassword("testgb-2021")
                .loginButton.click();

        new ToolbarMenu(driver).openToolbarItem("Друзья");

        driver.get(BASE_URL);
        new MainSideNavigationMenu(driver).getSideMenuItem("Заметки").click();
        new PortletSideNavigationMenu(driver).getPortletMenuItem("Скрытые").click();

        Thread.sleep(5000);
    }
}
