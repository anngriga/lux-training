package com.luxoft;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LuxTrainingTest {

    static SetUp setUpObject;

    @BeforeAll
    static void setUp() throws MalformedURLException {
        Path pathToDriver = Path.of("lib", "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(false);
        URL startUrl = new URL("https://www.luxoft-training.ru/");
        setUpObject = new SetUp(pathToDriver, options, startUrl);
    }

    @AfterEach
    void afterEach() {
        setUpObject.clearState();
    }

    @Test
    void testLogIn() {
        HomePage homePage = new HomePage(setUpObject.getDriver());
        homePage.openLoginForm();
        LoginForm loginForm = new LoginForm(setUpObject.getDriver());
        Assertions.assertTrue(loginForm.allInputsAreVisible());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/links.csv", numLinesToSkip = 1, delimiter = '|')
    void testSomething(String first, String second, String third) {
        HomePage homePage = new HomePage(setUpObject.getDriver());
        Set<String> expectedLinkTexts = Set.of(first, second, third);
        Assertions.assertEquals(
                expectedLinkTexts,
                homePage.linksWithTextFound(expectedLinkTexts),
                "Ссылки в главном меню не совпадают с ожидаемыми"
        );
    }

    @AfterAll
    static void tearDown() {
        setUpObject.getDriver().close();
    }

}
