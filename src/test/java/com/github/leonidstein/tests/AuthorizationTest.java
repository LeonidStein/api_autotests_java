package com.github.leonidstein.tests;

import com.github.leonidstein.models.request.user.create.NewUserModel;
import com.github.leonidstein.resolvers.UserResolver;
import com.github.leonidstein.resolvers.UserVariant;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDifferentToken;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatusCode;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasToken;
import static com.github.leonidstein.builders.UserBuilder.buildUser;
import static com.github.leonidstein.config.ConfigManager.config;
import static com.github.leonidstein.data.FakeDataManager.getEmptyString;
import static com.github.leonidstein.data.FakeDataManager.getNullString;
import static com.github.leonidstein.resolvers.UserType.TEST_USER;
import static com.github.leonidstein.resolvers.UserVariantType.WITHOUT_GAME;
import static com.github.leonidstein.utils.annotations.TestType.Type.NEGATIVE;
import static com.github.leonidstein.utils.annotations.TestType.Type.POSITIVE;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@Epic("API. jwt-authentication-controller")
@Feature("API. Авторизация пользователя и получение JWT токена")
@ExtendWith(UserResolver.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public final class AuthorizationTest extends BaseTest {

    private static final String LOGIN_TEST_USER = config().loginTestUserHasNotGames();
    private static final String PASSWORD_TEST_USER = config().passwordTestUserHasNotGames();

    private static Stream<Arguments> userCredentialsIsNull() {

        return Stream.of(
                Arguments.of(LOGIN_TEST_USER, getNullString()),
                Arguments.of(getNullString(), PASSWORD_TEST_USER)
        );
    }

    private static Stream<Arguments> userCredentialsIsEmpty() {

        return Stream.of(
                Arguments.of(LOGIN_TEST_USER, getEmptyString()),
                Arguments.of(getEmptyString(), PASSWORD_TEST_USER)
        );
    }

    @DisplayName("Авторизация пользователя и получение токена")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/20")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(CRITICAL)
    @TestType(POSITIVE)
    @Test
    public void testAuthUserAndGetToken(
            @UserVariant(user = TEST_USER, variant = WITHOUT_GAME) final NewUserModel user) {

        userService
                .makeRequest()
                    .authorization(user)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasToken());
    }

    @DisplayName("Проверка смены токена при повторной авторизации пользователя")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/51")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(CRITICAL)
    @TestType(POSITIVE)
    @Test
    public void testTokenChangesWhenUserReLogs(
            @UserVariant(user = TEST_USER, variant = WITHOUT_GAME) final NewUserModel user) {

        final String firstToken = userService.makeRequest()
                                                .authorization(user)
                                             .checkResponse()
                                                .should(hasStatusCode(SC_OK))
                                                .should(hasToken())
                                             .extractToken();

        userService
                .makeRequest()
                    .authorizationWithWaiting(user)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasToken())
                    .should(hasDifferentToken(firstToken));
    }

    @DisplayName("Авторизация пользователя с паролем или логином равным null")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/22")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(CRITICAL)
    @TestType(NEGATIVE)
    @ParameterizedTest
    @MethodSource("userCredentialsIsNull")
    public void testAuthUserWithCredentialsIsNull(final String login, final String password) {

        final NewUserModel user = buildUser(login, password);

        userService
                .makeRequest()
                    .authorization(user)
                .checkResponse()
                    .should(hasStatusCode(SC_INTERNAL_SERVER_ERROR));
    }

    @DisplayName("Авторизация пользователя с паролем или логином равной пустой строке")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/21")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(CRITICAL)
    @TestType(NEGATIVE)
    @ParameterizedTest
    @MethodSource("userCredentialsIsEmpty")
    public void testAuthUserWithCredentialsIsEmpty(final String login, final String password) {

        final NewUserModel user = buildUser(login, password);

        userService
                .makeRequest()
                    .authorization(user)
                .checkResponse()
                    .should(hasStatusCode(SC_UNAUTHORIZED));
    }
}
