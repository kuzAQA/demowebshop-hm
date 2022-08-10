import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.WebConfig;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {
    static WebConfig config = ConfigFactory.create(WebConfig.class, System.getProperties());

    @BeforeAll
    static void beforeAllTests() {
        String urlSelenoid;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        RestAssured.baseURI = config.getBaseUrl();

        Configuration.browser = config.browser();
        Configuration.baseUrl = config.getBaseUrl();

        if(config.getRemoteUrl() != null) {
            urlSelenoid = new StringBuilder(config.getRemoteUrl())
                    .insert(8, config.getSelenideLogin() + ":" + config.getSelenidePass() + "@")
                    .toString();
            Configuration.remote = urlSelenoid;
        }
    }

    @AfterEach
    @DisplayName("Прикрепленные материалы")
    void addAttachment() {
        Attach.screenshotAs("Last screenshot"); //добавление скриншота
        Attach.pageSource(); //добавление исходного кода страницы
        Attach.browserConsoleLogs(); //добавление логов из консоли браузера
        Attach.addVideo(); //добавление видео тесткейса
        closeWebDriver();
    }
}
