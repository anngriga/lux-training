package com.luxoft.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomePage extends BasePage {

    @FindBy(xpath = "//div[@class='header__control _nav']/*[local-name() = 'svg'][1]")
    private WebElement loginMenu;

    @FindBy(className = "two-links")
    private WebElement loginLink;

    @FindBy(xpath = "//ul[@class='navigation__list']")
    private WebElement mainMenu;

    public HomePage(WebDriver driver, WebDriverWait wait, JavascriptExecutor executor) {
        super(driver, wait, executor);
        PageFactory.initElements(driver, this);
    }

    @Step("Открыть форму логина")
    public LoginForm openLoginForm() {
        loginMenu.click();
        loginLink.click();
        return new LoginForm(driver);
    }

    /**
     * Открыть страницу Каталога курсов
     */
    @Step("Открыть страницу каталога курсов")
    public CataloguePage openCataloguePage() {
        findLinkWithText("Каталог").click();
        return new CataloguePage(driver, wait, executor);
    }

    /**
     * Найти ссылки в главном меню из переданного параметра.
     * @param texts Ссылки, которые нужно найти.
     * @return Set ссылок, которые были найдены по запросу
     */
    @Step("Найти ссылки в главном меню из переданного параметра {texts}")
    public Set<String> linksWithTextFound(Set<String> texts) {

        List<WebElement> links = mainMenu.findElements(By.tagName("a"));
        Set<String> result = new HashSet<>();
        for (WebElement elem : links) {
            String linkText = elem.getText();
            if (texts.contains(linkText)) {
                result.add(linkText);
            }
        }

        return result;

    }

    /**
     * Найти несоответствия в текстах ссылок главного меню и
     * переданных текстах
     * @param texts Текста ссылок для проверки
     * @return Список несоответствий с обоих сторон.
     */
    @Step("Найти несоответствия в текстах ссылок главного меню и переданных текстах: {texts}")
    Set<String> linkTextsMismatches(Set<String> texts) {

        List<WebElement> links = mainMenu.findElements(By.tagName("a"));
        Set<String> linkTexts = new HashSet<>();
        for (WebElement elem : links) {
            linkTexts.add(elem.getText());
        }

        Set<String> wholeSet = new HashSet<>(texts);
        wholeSet.addAll(linkTexts);
        linkTexts.retainAll(texts);
        wholeSet.removeAll(linkTexts);
        return wholeSet;

    }

}
