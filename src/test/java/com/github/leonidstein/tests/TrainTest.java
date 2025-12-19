package com.github.leonidstein.tests;

import com.github.leonidstein.utils.annotations.TestType;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.github.leonidstein.asserts.conditions.ConditionManager.hasApiVersion;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasCars;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatusCode;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasVariousKey;
import static com.github.leonidstein.utils.annotations.TestType.Type.POSITIVE;
import static io.qameta.allure.SeverityLevel.TRIVIAL;
import static org.apache.http.HttpStatus.SC_OK;

@Epic("API. response-train-controller")
@Feature("API. Тренировка")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public final class TrainTest extends BaseTest {

    @DisplayName("Получение списка с машинами")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/156")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(TRIVIAL)
    @TestType(POSITIVE)
    @Test
    public void testGetListWithCars() {

        trainService
                .makeRequest()
                    .getListWithCars()
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasCars());
    }

    @DisplayName("Получение различных вариантов ключей")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/157")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(TRIVIAL)
    @TestType(POSITIVE)
    @Test
    public void testGetVariousKey() {

        trainService
                .makeRequest()
                    .getVariousKey()
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasVariousKey());
    }

    @Flaky
    @DisplayName("Перенаправление на сайт")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/158")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(TRIVIAL)
    @TestType(POSITIVE)
    @Test
    public void testGetRedirect() {

        trainService
                .makeRequest()
                    .getRedirect()
                .checkResponse()
                    .should(hasStatusCode(SC_OK));
    }

    @DisplayName("Получение актуальной версии API приложения")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/159")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(TRIVIAL)
    @TestType(POSITIVE)
    @Test
    public void testGetApiVersion() {

        final String expectedApiVersion = "1.0.2";

        trainService
                .makeRequest()
                    .getCurrentApiAppVersion()
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasApiVersion(expectedApiVersion));
    }
}
