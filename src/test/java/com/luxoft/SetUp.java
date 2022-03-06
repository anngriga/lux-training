package com.luxoft;

import lombok.Data;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@Data
public class SetUp {

    private final String startUrlString;

    private final ChromeDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor javascriptExecutor;

    public SetUp(Path path, ChromeOptions options, URL startUrl) {

        this.startUrlString = startUrl.toString();
        System.setProperty("webdriver.chrome.driver", path.toString());

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 15);
        javascriptExecutor = driver;

        driver.navigate().to(startUrl);

    }

    void clearState() {
        while (driver.getWindowHandles().size() > 1) {
            driver.close();
        }
        driver.switchTo().window(
                driver.getWindowHandles().iterator().next()
        );
        driver.navigate().to(startUrlString);
    }

}
