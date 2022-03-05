package com.luxoft;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomePage {

    private final WebDriver driver;

    @FindBy(xpath = "//div[@class='header__control _nav']/*[local-name() = 'svg'][1]")
    private WebElement loginMenu;

    @FindBy(className = "two-links")
    private WebElement loginLink;

    @FindBy(xpath = "//ul[@class='navigation__list']")
    private WebElement mainMenu;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    void openLoginForm() {
        loginMenu.click();
        loginLink.click();
    }

    /**
     * Открыть PDF-файл каталога курсов. Откроется в новой вкладке,
     * которая будет неактивна в драйвере
     */
    void openCataloguePDF() {
        findLinkWithText("Каталог").click();
        findLinkWithText("Скачать каталог").click();
        switchTab();
    }

    /**
     * Переключиться на не текущую вкладку браузера :)
     */
    void switchTab() {
        Set<String> tabs = driver.getWindowHandles();
        String currentTab = driver.getWindowHandle();
        for (String tab : tabs) {
            if (tab.equals(currentTab)) {
                continue;
            }
            driver.switchTo().window(tab);
            break;
        }
    }

    /**
     * Получить тип содержимого плагина на текущей вкладке.
     * @return Тип содержимого.
     */
    String getPluginContents() {
        return driver.findElement(By.xpath("//embed"))
                .getAttribute("type");
    }

    WebElement findLinkWithText(String text) {
        String xpath = "//a[(@href or @onclick) and contains(text(), '"+ text + "')]";
        return driver.findElement(By.xpath(xpath));
    }

    /**
     * Найти ссылку с заданным текстом и кликнуть по ней.
     * @param text Текст для поиска
     */
    void clickOnLinkWithText(String text) {
        findLinkWithText(text).click();
    }

    String getElementColor(WebElement elem) {
        return elem.getCssValue("color");
    }

    void pointOnLink(WebElement elem) {
        Actions actions = new Actions(driver);
        actions.moveToElement(elem).build().perform();
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
