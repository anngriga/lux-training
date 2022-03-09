package com.luxoft.pages;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

/**
 * Общий класс для всех страниц
 */
@RequiredArgsConstructor
public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final JavascriptExecutor executor;

    /**
     * Переключиться на не текущую вкладку браузера :)
     */
    @Step("Переключиться на другую вкладку")
    protected void switchTab() {
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
    @Step("Получить тип содержимого плагина на текущей вкладке")
    public String getPluginContents() {
        return driver.findElement(By.xpath("//embed"))
                .getAttribute("type");
    }

    @Step("Найти ссылку с текстом: {text}")
    public WebElement findLinkWithText(String text) {
        String xpath = "//a[(@href or @onclick) and contains(text(), '"+ text + "')]";
        return driver.findElement(By.xpath(xpath));
    }

    /**
     * Найти ссылку с заданным текстом и кликнуть по ней.
     * @param text Текст для поиска
     */
    @Step("Кликнуть по ссылке с текстом: {text}")
    public void clickOnLinkWithText(String text) {
        findLinkWithText(text).click();
    }

    @Step("Получить цвет элемента")
    public String getElementColor(WebElement elem) {
        return elem.getCssValue("color");
    }

    @Step("Навести курсор на элемент страницы")
    public void pointOnLink(WebElement elem) {
        Actions actions = new Actions(driver);
        actions.moveToElement(elem).build().perform();
    }

}
