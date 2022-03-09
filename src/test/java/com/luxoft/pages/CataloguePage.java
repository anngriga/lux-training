package com.luxoft.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CataloguePage extends BasePage {

    @FindBy(xpath = "//input[@name='q']")
    private WebElement searchInput;

    @FindBy(xpath = "//div[@class='title-search-result']")
    private WebElement searchResults;

    public CataloguePage(WebDriver driver, WebDriverWait wait, JavascriptExecutor executor) {
        super(driver, wait, executor);
        PageFactory.initElements(driver, this);
    }

    /**
     * Найти ссылку на курс по заданному имени курса
     * @param name Имя курса для поиска
     * @return URL найденного курса
     */
    @Step("Найти ссылку на курс по заданному имени курса: {name}")
    public String findCourseLink(String name) {
        searchInput.sendKeys(name);
        WebElement courseLink = wait.until(ExpectedConditions.visibilityOf(searchResults))
                .findElement(By.xpath("//div[@class='title-search-result']//a[@href]"));
        return courseLink.getAttribute("href");
    }

    /**
     * Открыть ссылку (на курс) в новой вкладке и перейти в эту вкладку.
     * @param url Адрес ссылки, которую нужно открыть
     */
    @Step("Открыть ссылку на курс: {url}")
    public CoursePage openURLInNewTab(String url) {
        // window.open(url, '_blank').focus();
        String js = "window.open('" + url + "', '_blank').focus();";
        executor.executeScript(js);
        switchTab();
        return new CoursePage(driver, wait, executor);
    }

    /**
     * Открыть PDF-файл каталога курсов. Откроется в новой вкладке,
     * которая будет неактивна в драйвере
     */
    @Step("Открыть PDF-файл каталога курсов.")
    public void openCataloguePDF() {
        findLinkWithText("Скачать каталог").click();
        switchTab();
    }

}
