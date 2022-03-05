package com.luxoft;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomePage {

    @FindBy(xpath = "//div[@class='header__control _nav']/*[local-name() = 'svg'][1]")
    WebElement loginMenu;

    @FindBy(className = "two-links")
    WebElement loginLink;

    @FindBy(xpath = "//ul[@class='navigation__list']")
    WebElement mainMenu;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    void openLoginForm() {
        loginMenu.click();
        loginLink.click();
    }

    /**
     * Найти ссылки в главном меню из переданного параметра.
     * @param texts Ссылки, которые нужно найти.
     * @return Set ссылок, которые были найдены по запросу
     */
    Set<String> linksWithTextFound(Set<String> texts) {

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
