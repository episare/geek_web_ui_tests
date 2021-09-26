package ru.geekbrains.lesson6.crm;

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

    public LoginPage fillInputLogin(String login) {
        inputLogin.sendKeys(login);
        return this;
    }

    public LoginPage fillInputPassword(String password) {
        inputPassword.sendKeys(password);
        return this;
    }

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
