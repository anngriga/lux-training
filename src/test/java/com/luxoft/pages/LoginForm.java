package com.luxoft.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginForm extends BasePage {

    @FindBy(id = "lb01")
    WebElement loginInput;

    @FindBy(id = "lb02")
    WebElement passwordInput;

    @FindBy(xpath = "//button[@name='Login']")
    WebElement logInButton;

    @FindBy(id = "lb03-styler")
    WebElement rememberMeCheckbox;

    public LoginForm(WebDriver driver) {
        super(driver, null, null);
        PageFactory.initElements(driver, this);
    }

    @Step("Проверить что все элементы формы отображаются")
    public boolean allInputsAreVisible() {
        return loginInput.isDisplayed() && passwordInput.isDisplayed() &&
                logInButton.isDisplayed() && rememberMeCheckbox.isDisplayed();
    }

}
