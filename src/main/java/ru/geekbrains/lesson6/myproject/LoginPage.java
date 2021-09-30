package ru.geekbrains.lesson6.myproject;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BaseView {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "st.email")
    public WebElement loginEmail;

    @Step("Вводим логин, в качестве логина используется адрес электронной почты")
    public LoginPage fillLogin(String login) {
        loginEmail.sendKeys(login);
        return this;
    }

    @FindBy(name = "st.password")
    public WebElement loginPassword;

    @Step("Вводим пароль")
    public LoginPage fillPassword(String password) {
        loginPassword.sendKeys(password);
        return this;
    }

    @FindBy(xpath = "//input[@class='button-pro __wide']")
    public WebElement loginButton;


}
