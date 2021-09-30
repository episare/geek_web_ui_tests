package ru.geekbrains.lesson6.crm;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.geekbrains.lesson6.crm.expense.CreateExpensePage;
import ru.geekbrains.lesson6.crm.expense.ExpensesRequestsPage;
import ru.geekbrains.lesson6.crm.expense.ExpensesSubmenu;
import ru.geekbrains.lesson6.crm.project.CreateProjectPage;
import ru.geekbrains.lesson6.crm.project.ProjectPage;
import ru.geekbrains.lesson6.crm.project.ProjectSubmenu;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.htmlelements.matchers.common.IsElementDisplayedMatcher.isDisplayed;

@DisplayName("CRM - Тест функций навигационного меню")
public class PageObjectTest extends BaseTest {


    @Epic(value = "Навигационное меню")
    @Feature(value = "Заявки на расходы")
    @Story(value = "Новая заявка")
   @Description(value = "Тест проверяет создание заявки на расходы")
    @Test
    void createCrmExpenseRequestTest() throws InterruptedException {
        driver.get("https://crm.geekbrains.space/user/login");
        new LoginPage(driver)
                .fillInputLogin("Applanatest1")     // fillInputLogin() returns LoginPage
                .fillInputPassword("Student2020!")  // fillInputPassword() returns LoginPage
                .clickLoginButton()     // clickLoginButton() returns MainPage
                .navigationMenu.openNavigationMenuItem("Расходы");  // Method from MainPage

        new ExpensesSubmenu(driver).goToExpensesRequestsPage();

        new ExpensesRequestsPage(driver)
                .createExpense()
                .fillName("test")
                .selectBusinessUnit("Research & Development")
                .selectExpenditure("01103 - ОС: мебель")
                .selectCurrency("Евро")
                .fillSumPlan("300")
                .selectType("Безналичный")
                .selectDateUntilExecute("20")
                .selectDatePlan("15")
                .selectSubcontract("№23455")
                .selectNds("НДС (20%)")
                .expenseSaveAndCloseButton.click();

        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By
                .xpath("//div[contains(text(),'Загрузка')]")));
        assertThat(new CreateExpensePage(driver).confirmSavedSuccessfullyElement, isDisplayed());

    }


    @Epic(value = "Навигационное меню")
    @Feature(value = "Проекты")
    @Story(value = "Новый проект")
    @Description(value = "Тест проверяет создание нового проекта")
    @Test
    void createCrmProjectTest() throws InterruptedException {
        driver.get("https://crm.geekbrains.space/user/login");
        step("Авторизируемся и открываем навигационное меню");
        new LoginPage(driver)
                .fillInputLogin("Applanatest1")     // fillInputLogin() returns LoginPage
                .fillInputPassword("Student2020!")  // fillInputPassword() returns LoginPage
                .clickLoginButton()     // clickLoginButton() returns MainPage
                .navigationMenu.openNavigationMenuItem("Проекты");  // Method from MainPage

        step("Переходим на страницу Проекты");
        new ProjectSubmenu(driver).goToProjectPage();

        step("Создаем новый проект");
        new ProjectPage(driver)
                .createProject()
                .fillProjectName("Test" + System.currentTimeMillis())
                .selectOrganisationToPattern("123")
                .selectPriority("Низкий")
                .selectFinanceSource("Внутреннее")
                .selectBusynessUnit("Research & Development")
                .selectCurator("Applanatest Applanatest Applanatest")
                .selectRp("Applanatest2 Applanatest2 Applanatest2")
                .selectManager("Applanatest1 Applanatest1 Applanatest1")
                .selectContactToPattern("1111")
                .buttonSaveAndClose.click();

        step("Ждем завершения загрузки созданного проекта");
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By
                .xpath("//div[contains(text(),'Загрузка')]")));
        step("Проверяем подтверждение успешной загрузки");
        assertThat(new CreateProjectPage(driver).confirmSaveProjectMessage, isDisplayed());
        Thread.sleep(5000);
    }
}
