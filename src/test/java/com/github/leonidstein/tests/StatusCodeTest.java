package com.github.leonidstein.tests;

import com.github.leonidstein.utils.annotations.TestType;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDescription;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatusCode;
import static com.github.leonidstein.constants.info.Description.BAD_REQUEST;
import static com.github.leonidstein.constants.info.Description.CREATED;
import static com.github.leonidstein.constants.info.Description.FORBIDDEN;
import static com.github.leonidstein.constants.info.Description.MOVED_PERMANENTLY;
import static com.github.leonidstein.constants.info.Description.NOT_FOUND;
import static com.github.leonidstein.utils.annotations.TestType.Type.POSITIVE;
import static io.qameta.allure.SeverityLevel.TRIVIAL;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_MOVED_PERMANENTLY;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@Epic("API. status-codes-controller")
@Feature("API. Получение статус кодов")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public final class StatusCodeTest extends BaseTest {

    @DisplayName("Получение 400 статус кода")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/148")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(TRIVIAL)
    @TestType(POSITIVE)
    @Test
    public void testGetBadRequest() {

        statusCodeService
                .makeRequest()
                    .getBadRequest()
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST))
                    .should(hasDescription(BAD_REQUEST.getDescription()));
    }

    @DisplayName("Получение 201 статус кода")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/149")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(TRIVIAL)
    @TestType(POSITIVE)
    @Test
    public void testGetCreated() {

        statusCodeService
                .makeRequest()
                    .getCreated()
                .checkResponse()
                    .should(hasStatusCode(SC_CREATED))
                    .should(hasDescription(CREATED.getDescription()));
    }

    @DisplayName("Получение 403 статус кода")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/150")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(TRIVIAL)
    @TestType(POSITIVE)
    @Test
    public void testGetForbidden() {

        statusCodeService
                .makeRequest()
                    .getForbidden()
                .checkResponse()
                    .should(hasStatusCode(SC_FORBIDDEN))
                    .should(hasDescription(FORBIDDEN.getDescription()));
    }

    @DisplayName("Получение 404 статус кода")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/151")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(TRIVIAL)
    @TestType(POSITIVE)
    @Test
    public void testGetNotFound() {

        statusCodeService
                .makeRequest()
                    .getNotFound()
                .checkResponse()
                    .should(hasStatusCode(SC_NOT_FOUND))
                    .should(hasDescription(NOT_FOUND.getDescription()));
    }

    @DisplayName("Получение 301 статус кода")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/152")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(TRIVIAL)
    @TestType(POSITIVE)
    @Test
    public void testGetMovedPermanently() {

        statusCodeService
                .makeRequest()
                    .getMovedPermanently()
                .checkResponse()
                    .should(hasStatusCode(SC_MOVED_PERMANENTLY))
                    .should(hasDescription(MOVED_PERMANENTLY.getDescription()));
    }

    @DisplayName("Получение 204 статус кода")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/153")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(TRIVIAL)
    @TestType(POSITIVE)
    @Test
    public void testGetNoContent() {

        statusCodeService
                .makeRequest()
                    .getNoContent()
                .checkResponse()
                    .should(hasStatusCode(SC_NO_CONTENT));
    }

    @DisplayName("Получение 401 статус кода")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/154")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(TRIVIAL)
    @TestType(POSITIVE)
    @Test
    public void testGetUnauthorized() {

        statusCodeService
                .makeRequest()
                    .getUnauthorized()
                .checkResponse()
                    .should(hasStatusCode(SC_UNAUTHORIZED));
    }
}
