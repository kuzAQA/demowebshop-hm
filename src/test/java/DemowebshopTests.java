import com.codeborne.selenide.WebDriverRunner;
import config.AuthData;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class DemowebshopTests extends TestBase {
    static AuthData config = ConfigFactory.create(AuthData.class, System.getProperties());
    final String authTokenName = "NOPCOMMERCE.AUTH";

    @Test
    @Tag("authUI")
    @DisplayName("Успешная авторизация на demowebshop, через UI")
    void loginTest() {
        step("Открыть страницу логина", () -> open("/login"));
        step("Ввод логина и пароля", () -> {
            $("#Email").setValue(config.email());
            $("#Password").setValue(config.pass()).pressEnter();
        });
        step("Проверка успешной авторизации", () -> {
            $(".account").shouldHave(text(config.email()));
        });
    }

    @Test
    @Tag("authUiAndApi")
    @DisplayName("Успешная авторизация по api, проверка на UI")
    void loginApiTest() {
        step("Получение токена авторизации по api и установка в браузер", () -> {
            String authToken = given()
                    .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", config.email())
                    .formParam("Password", config.pass())
                    .log().all()
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(authTokenName);

            step("Открытие минимального контента на сайте, для установки cookie", () -> open("/Themes/DefaultClean/Content/images/logo.png"));

            step("Установка токена в браузер", () -> {
                Cookie authCookie = new Cookie(authTokenName, authToken);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
            });
        });
        step("Проверка успешной авторизации", () -> {
            open("");
            $(".account").shouldHave(text(config.email()));
        });
    }
}
