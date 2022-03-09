package com.luxoft.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CoursePage extends BasePage {

    public CoursePage(WebDriver driver, WebDriverWait wait, JavascriptExecutor executor) {
        super(driver, wait, executor);
        PageFactory.initElements(driver, this);
    }


    /**
     * Найти количество видимых кнопок с текстом "Записаться на курс"
     * @return Количество отображаемых кнопок
     */
    @Step("Найти количество видимых кнопок с текстом \"Записаться на курс\"")
    public int findCountOfVisibleEnrollButtons() {

        List<WebElement> buttons = driver.findElements(
                By.xpath("//div[not (@style)]/span[text()='Записаться на курс']")
        );

        int visibleCount = 0;
        for (WebElement btn : buttons) {
            if (btn.isDisplayed()) {
                visibleCount++;
            }
        }
        return visibleCount;

    }

}
