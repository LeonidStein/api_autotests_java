package com.github.leonidstein.tests;

import com.github.leonidstein.models.request.user.create.NewUserModel;
import com.github.leonidstein.resolvers.UserResolver;
import com.github.leonidstein.resolvers.UserVariant;
import com.github.leonidstein.utils.annotations.TestType;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.BeforeEach;
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

import static com.github.leonidstein.asserts.conditions.ConditionManager.hasMessage;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatus;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatusCode;
import static com.github.leonidstein.builders.UserBuilder.buildUser;
import static com.github.leonidstein.constants.info.Message.BODY_HAS_NO_PASSWORD_PARAMETER;
import static com.github.leonidstein.constants.info.Message.USER_PASSWORD_SUCCESSFULLY_CHANGED;
import static com.github.leonidstein.constants.info.Status.FAIL;
import static com.github.leonidstein.constants.info.Status.SUCCESS;
import static com.github.leonidstein.data.FakeData.INVALID_PASSWORD_LENGTH;
import static com.github.leonidstein.data.FakeDataManager.getEmptyString;
import static com.github.leonidstein.data.FakeDataManager.getInvalidLongPassword;
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
import static com.github.leonidstein.utils.annotations.TestType.Type.NEGATIVE;
import static com.github.leonidstein.utils.annotations.TestType.Type.POSITIVE;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@Epic("API. user-controller-new")
@Feature("API. Обновление пароля пользователя")
@ExtendWith(UserResolver.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public final class PasswordUserTest extends BaseTest {

    private String token;
    private String newPassword;
    private NewUserModel user;

    @BeforeEach
    void initUserCredentials(@UserVariant(user = NEW_USER, variant = WITHOUT_GAME) final NewUserModel user) {

        this.token = userService.makeRequest()
                                    .registrationWithAuthorization(user)
                                .checkResponse()
                                    .should(hasStatusCode(SC_OK))
                                .extractToken();

        this.newPassword = getPassword();
        this.user = new NewUserModel(user.getLogin(), newPassword);
    }

    private static Stream<Arguments> invalidUserPassword() {

        return Stream.of(
                Arguments.of(getNullString()),
                Arguments.of(getEmptyString())
        );
    }

    private static Stream<Arguments> validUserPassword() {

        return Stream.of(
                Arguments.of(getRandomString(getRandomIntFromTo(1, INVALID_PASSWORD_LENGTH))),
                Arguments.of(getRandomSpecChar(getRandomIntFromTo(1, INVALID_PASSWORD_LENGTH))),
                Arguments.of(getRandomStringNumber(getRandomIntFromTo(1, INVALID_PASSWORD_LENGTH))),
                Arguments.of(getRandomLetter(getRandomIntFromTo(1, INVALID_PASSWORD_LENGTH))),
                Arguments.of(getValidLongPasswordBoundaryValue())
        );
    }

    @DisplayName("Обновление пароля пользователя")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/34")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(CRITICAL)
    @TestType(POSITIVE)
    @ParameterizedTest
    @MethodSource("validUserPassword")
    public void testUpdateUserPassword(final String newPassword) {

        final NewUserModel user = buildUser(this.user.getLogin(), newPassword);

        userService
                .makeRequest()
                    .updateUserPassword(token, newPassword)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(USER_PASSWORD_SUCCESSFULLY_CHANGED.getMessage()));

        userService
                .makeRequest()
                    .authorization(user)
                .checkResponse()
                    .should(hasStatusCode(SC_OK));
    }

    @DisplayName("Обновление невалидного пароля пользователя")
    @Description("Невалидный пароль: null, пустая строка")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/59")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(CRITICAL)
    @TestType(NEGATIVE)
    @ParameterizedTest
    @MethodSource("invalidUserPassword")
    public void testUpdateUserInvalidPassword(final String invalidPassword) {

        userService
                .makeRequest()
                    .updateUserPassword(token, invalidPassword)
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST))
                    .should(hasStatus(FAIL.getStatus()))
                    .should(hasMessage(BODY_HAS_NO_PASSWORD_PARAMETER.getMessage()));
    }

    @DisplayName("Обновление невалидного пароля длиной в 256 символов")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/60")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(CRITICAL)
    @TestType(NEGATIVE)
    @Test
    public void testUpdateUserLongPassword() {

        final String invalidLongPassword = getInvalidLongPassword();

        userService
                .makeRequest()
                    .updateUserPassword(token, invalidLongPassword)
                .checkResponse()
                    .should(hasStatusCode(SC_INTERNAL_SERVER_ERROR));
    }

    @DisplayName("Обновление пароля пользователя без токена авторизации")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/35")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testUpdateUserPasswordWithoutToken() {

        userService
                .makeRequest()
                    .updateUserPasswordWithoutToken(newPassword)
                .checkResponse()
                    .should(hasStatusCode(SC_UNAUTHORIZED));
    }

}
