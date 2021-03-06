package com.luxoft;

import com.luxoft.pages.CataloguePage;
import com.luxoft.pages.CoursePage;
import com.luxoft.pages.HomePage;
import com.luxoft.pages.LoginForm;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Set;

@Epic("Тестирование luxoft-training")
@Story("Смок тестирование luxoft-training")
@ExtendWith(AllureScreenshotExtension.class)
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

    static WebDriver getDriverForAllure() {
        if (setUpObject == null) {
            return null;
        }
        return setUpObject.getDriver();
    }

    private HomePage makeHomePage() {
        return new HomePage(
                setUpObject.getDriver(),
                setUpObject.getWait(),
                setUpObject.getJavascriptExecutor()
        );
    }

    @Test
    @DisplayName("Тест формы логина")
    void testLogIn() {
        HomePage homePage = makeHomePage();
        LoginForm loginForm = homePage.openLoginForm();
        Assertions.assertTrue(loginForm.allInputsAreVisible());
    }

    @ParameterizedTest(name = "Тест набора ссылок главного меню {0}, {1}, {2}")
    @CsvFileSource(resources = "/links.csv", numLinesToSkip = 1, delimiter = '|')
    @DisplayName("Тест набора ссылок главного меню")
    void testMainMenuLinks(String first, String second, String third) {
        HomePage homePage = makeHomePage();
        Set<String> expectedLinkTexts = Set.of(first, second, third);
        Assertions.assertEquals(
                expectedLinkTexts,
                homePage.linksWithTextFound(expectedLinkTexts),
                "Ссылки в главном меню не совпадают с ожидаемыми"
        );
    }

    @ParameterizedTest(name = "Тест изменения цвета ссылок при наведении: {0}, {1}")
    @CsvFileSource(resources = "/linktexts.csv", numLinesToSkip = 1, delimiter = '|')
    @DisplayName("Тест изменения цвета ссылок при наведении")
    void testLinkColors(String url, String linkText) {

        setUpObject.getDriver().navigate().to(url);
        HomePage homePage = makeHomePage();
        WebElement link = homePage.findLinkWithText(linkText);
        Assertions.assertNotEquals(
                "rgba(242, 111, 33, 1)",
                homePage.getElementColor(link),
                "Ссылка подсвечена до наведения на неё"
        );
        homePage.pointOnLink(link);
        Assertions.assertEquals(
                "rgba(242, 111, 33, 1)",
                homePage.getElementColor(link),
                "Цвет ссылки не изменился после наведения"
        );
        homePage.clickOnLinkWithText(linkText);

    }

    @Test
    @DisplayName("Тест каталога курсов в формате PDF")
    void testCatalogue() {
        HomePage homePage = makeHomePage();
        CataloguePage cataloguePage = homePage.openCataloguePage();
        cataloguePage.openCataloguePDF();
        Assertions.assertEquals(
                "application/pdf",
                homePage.getPluginContents(),
                "Содержимое плагина не в формате PDF!"
        );
    }

    @ParameterizedTest(name = "Тест кнопок записи на курс: {0}")
    @ValueSource(strings = {"SQA-050", "SQA-004"})
    @DisplayName("Тест кнопок записи на курс")
    void testCatalogueEnrollLinks(String courseName) {
        HomePage homePage = makeHomePage();
        CataloguePage cataloguePage = homePage.openCataloguePage();
        String courseUrl = cataloguePage.findCourseLink(courseName);
        CoursePage coursePage = cataloguePage.openURLInNewTab(courseUrl);
        Assertions.assertEquals(
                2,
                coursePage.findCountOfVisibleEnrollButtons(),
                "Неверное количество кнопок записи на курс!"
        );
    }

    @AfterAll
    static void tearDown() {
        WebDriver driver = setUpObject.getDriver();
        for (String tab : driver.getWindowHandles()) {
            driver.switchTo().window(tab);
            driver.close();
        }
    }

}
