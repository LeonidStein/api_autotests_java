package com.github.leonidstein.tests;

import com.github.leonidstein.models.request.user.create.NewUserModel;
import com.github.leonidstein.resolvers.UserResolver;
import com.github.leonidstein.resolvers.UserVariant;
import com.github.leonidstein.utils.annotations.TestType;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
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

import static com.github.leonidstein.asserts.conditions.ConditionManager.hasGames;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasIDUser;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasLogin;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasMessage;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasNoGames;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatus;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatusCode;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasToken;
import static com.github.leonidstein.builders.UserBuilder.buildUser;
import static com.github.leonidstein.constants.info.Message.MISSING_LOGIN_OR_PASSWORD;
import static com.github.leonidstein.constants.info.Message.USER_CREATED;
import static com.github.leonidstein.constants.info.Status.FAIL;
import static com.github.leonidstein.constants.info.Status.SUCCESS;
import static com.github.leonidstein.data.FakeData.INVALID_LOGIN_LENGTH;
import static com.github.leonidstein.data.FakeData.INVALID_PASSWORD_LENGTH;
import static com.github.leonidstein.data.FakeDataManager.getEmptyString;
import static com.github.leonidstein.data.FakeDataManager.getInvalidLongLogin;
import static com.github.leonidstein.data.FakeDataManager.getInvalidLongPassword;
import static com.github.leonidstein.data.FakeDataManager.getLogin;
import static com.github.leonidstein.data.FakeDataManager.getNullString;
import static com.github.leonidstein.data.FakeDataManager.getPassword;
import static com.github.leonidstein.data.FakeDataManager.getRandomIntFromTo;
import static com.github.leonidstein.data.FakeDataManager.getRandomLetter;
import static com.github.leonidstein.data.FakeDataManager.getRandomSpecChar;
import static com.github.leonidstein.data.FakeDataManager.getRandomString;
import static com.github.leonidstein.data.FakeDataManager.getRandomStringNumber;
import static com.github.leonidstein.data.FakeDataManager.getValidLongPasswordBoundaryValue;
import static com.github.leonidstein.resolvers.UserType.NEW_USER;
import static com.github.leonidstein.resolvers.UserVariantType.WITHOUT_GAME;
import static com.github.leonidstein.resolvers.UserVariantType.WITH_GAME;
import static com.github.leonidstein.utils.annotations.TestType.Type.NEGATIVE;
import static com.github.leonidstein.utils.annotations.TestType.Type.POSITIVE;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_OK;

@Epic("API. user-controller-new")
@Feature("API. Регистрация нового пользователя")
@ExtendWith(UserResolver.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public final class RegistrationTest extends BaseTest {

    private static Stream<Arguments> validCredentialsUser() {

        return Stream.of(
                Arguments.of(getLogin(), getRandomSpecChar(getRandomIntFromTo(1, INVALID_PASSWORD_LENGTH))),
                Arguments.of(getLogin(), getRandomString(getRandomIntFromTo(1, INVALID_PASSWORD_LENGTH))),
                Arguments.of(getLogin(), getRandomStringNumber(getRandomIntFromTo(1, INVALID_PASSWORD_LENGTH))),
                Arguments.of(getLogin(), getRandomLetter(getRandomIntFromTo(1, INVALID_PASSWORD_LENGTH))),
                Arguments.of(getLogin(), getValidLongPasswordBoundaryValue()),

                Arguments.of(getRandomSpecChar(getRandomIntFromTo(1, INVALID_LOGIN_LENGTH)), getPassword()),
                Arguments.of(getRandomString(getRandomIntFromTo(1, INVALID_LOGIN_LENGTH)), getPassword()),
                Arguments.of(getRandomStringNumber(getRandomIntFromTo(1, INVALID_LOGIN_LENGTH)), getPassword()),
                Arguments.of(getRandomLetter(getRandomIntFromTo(1, INVALID_LOGIN_LENGTH)), getPassword()),
                Arguments.of(getValidLongPasswordBoundaryValue(), getPassword())
        );
    }

    private static Stream<Arguments> invalidCredentialsUser() {

        return Stream.of(
                Arguments.of(getLogin(), getEmptyString()),
                Arguments.of(getLogin(), getNullString()),

                Arguments.of(getEmptyString(), getPassword()),
                Arguments.of(getNullString(), getPassword())
        );
    }

    private static Stream<Arguments> invalidCredentialsUserMaxLength() {

        return Stream.of(
                Arguments.of(getInvalidLongLogin(), getPassword()),
                Arguments.of(getLogin(), getInvalidLongPassword())
        );
    }

    @DisplayName("Создание нового пользователя без игр")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/15")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(CRITICAL)
    @TestType(POSITIVE)
    @Test
    public void testCreateNewUserWithoutGames(@UserVariant(user = NEW_USER, variant = WITHOUT_GAME) final NewUserModel user) {

        userService
                .makeRequest()
                    .registration(user)
                .checkResponse()
                    .should(hasStatusCode(SC_CREATED))
                    .should(hasIDUser())
                    .should(hasLogin())
                    .should(hasNoGames())
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(USER_CREATED.getMessage()));

        userService
                .makeRequest()
                    .authorization(user)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasToken());
    }

    @DisplayName("Создание нового пользователя с валидными данными")
    @Description("Валидные логин или пароль: только спец. символы, рандом. строка, только цифры, только буквы, " +
            "граничное значение от максимального (255 символов)")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/155")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(CRITICAL)
    @TestType(POSITIVE)
    @ParameterizedTest
    @MethodSource("validCredentialsUser")
    public void testCreateNewUserWithValidFields(final String login, final String password) {

        final NewUserModel user = buildUser(login, password);

        userService
                .makeRequest()
                    .registration(user)
                .checkResponse()
                    .should(hasStatusCode(SC_CREATED))
                    .should(hasIDUser())
                    .should(hasLogin())
                    .should(hasNoGames())
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(USER_CREATED.getMessage()));

        userService
                .makeRequest()
                    .authorization(user)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasToken());
    }

    @DisplayName("Создание нового пользователя c играми")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/40")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(CRITICAL)
    @TestType(POSITIVE)
    @Test
    public void testCreateNewUserWithGames(
            @UserVariant(user = NEW_USER, variant = WITH_GAME) final NewUserModel user) {

        userService
                .makeRequest()
                    .registration(user)
                .checkResponse()
                    .should(hasStatusCode(SC_CREATED))
                    .should(hasIDUser())
                    .should(hasLogin())
                    .should(hasGames())
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(USER_CREATED.getMessage()));

        userService
                .makeRequest()
                    .authorization(user)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasToken());
    }

    @Issue("58981193")
    @DisplayName("Создание нового пользователя с невалидными данными")
    @Description("Невалидные данные: пустая строка вместо логина или пароля; null вместо логина или пароля")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/16")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(CRITICAL)
    @TestType(NEGATIVE)
    @ParameterizedTest
    @MethodSource("invalidCredentialsUser")
    public void testCreateNewUserWithInvalidFields(final String login, final String password) {

        final NewUserModel user = buildUser(login, password);

        userService
                .makeRequest()
                    .registration(user)
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST))
                    .should(hasMessage(MISSING_LOGIN_OR_PASSWORD.getMessage()))
                    .should(hasStatus(FAIL.getStatus()));
    }

    @DisplayName("Создание нового пользователя с невалидными данными")
    @Description("Невалидные данные: пароль или логин в 256 символов")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/96")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(CRITICAL)
    @TestType(NEGATIVE)
    @ParameterizedTest
    @MethodSource("invalidCredentialsUserMaxLength")
    public void testCreateNewUserWithInvalidFieldsMaxLength(final String login, final String password) {

        final NewUserModel user = buildUser(login, password);

        userService
                .makeRequest()
                    .registration(user)
                .checkResponse()
                    .should(hasStatusCode(SC_INTERNAL_SERVER_ERROR));
    }
}

