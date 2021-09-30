package ru.geekbrains.lesson6.crm;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BaseView{
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="prependedInput")
    public WebElement inputLogin;

    @FindBy(id="prependedInput2")
    public WebElement inputPassword;

    @FindBy (xpath="//button[@class='btn btn-large  btn-primary pull-right']")
    public WebElement inputButton;

    @Step("Заполнить поле логина")
    public LoginPage fillInputLogin(String login) {
        inputLogin.sendKeys(login);
        return this;
    }

    @Step("Заполнить поле пароля")
    public LoginPage fillInputPassword(String password) {
        inputPassword.sendKeys(password);
        return this;
    }

    @Step("Клик на кнопку логина")
    public MainPage clickLoginButton() {
        inputButton.click();
        return new MainPage(driver);
    }

    public void login (String login, String password) {
        inputLogin.sendKeys(login);
        inputPassword.sendKeys(password);
        inputButton.click();
    }

}
