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

import static com.github.leonidstein.asserts.conditions.ConditionManager.hasGames;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasIDUser;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasLogin;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasNoGames;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasRegisteredLogin;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatusCode;
import static com.github.leonidstein.resolvers.UserType.NEW_USER;
import static com.github.leonidstein.resolvers.UserType.TEST_USER;
import static com.github.leonidstein.resolvers.UserVariantType.WITHOUT_GAME;
import static com.github.leonidstein.resolvers.UserVariantType.WITH_GAME;
import static com.github.leonidstein.utils.annotations.TestType.Type.NEGATIVE;
import static com.github.leonidstein.utils.annotations.TestType.Type.POSITIVE;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@Epic("API. user-controller-new")
@Feature("API. Получение информации о пользователе и получение логинов зарегистрированных пользователей")
@ExtendWith(UserResolver.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public final class InfoUserTest extends BaseTest {
// @formatter:off

    @DisplayName("Получение информации о пользователе без игр")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/27")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(POSITIVE)
    @Test
    public void testGetInfoAboutUserWithoutGames(
            @UserVariant(user = TEST_USER, variant = WITHOUT_GAME) final NewUserModel user) {

        final String token = userService.makeRequest()
                                            .authorization(user)
                                        .checkResponse()
                                            .should(hasStatusCode(SC_OK))
                                        .extractToken();

        userService
                .makeRequest()
                    .getInfoAboutUser(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasLogin())
                    .should(hasIDUser())
                    .should(hasNoGames());
    }

    @DisplayName("Получение информации о пользователе с играми")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/57")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(POSITIVE)
    @Test
    public void testGetInfoAboutUserWithGames(
            @UserVariant(user = TEST_USER, variant = WITH_GAME) final NewUserModel user) {

        final String token = userService.makeRequest()
                                            .authorization(user)
                                        .checkResponse()
                                            .should(hasStatusCode(SC_OK))
                                        .extractToken();

        userService
                .makeRequest()
                    .getInfoAboutUser(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasLogin())
                    .should(hasIDUser())
                    .should(hasGames());
    }

    @DisplayName("Получение информации о пользователе без токена авторизации")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/58")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testGetInfoAboutUserWithoutToken() {

        userService
                .makeRequest()
                    .getInfoAboutUserWithoutToken()
                .checkResponse()
                    .should(hasStatusCode(SC_UNAUTHORIZED));
    }

    @DisplayName("Получение информации о последних 100 зарегистрированных пользователях " +
            "и проверка отображения пользователя в списке после регистрации")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/30")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(POSITIVE)
    @Test
    public void testGetLoginsLastRegisteredUsers(@UserVariant(user = NEW_USER, variant = WITHOUT_GAME)
    final NewUserModel user) {

        final String expectedLoginInList = user.getLogin();

        userService
                .makeRequest()
                    .registration(user)
                .checkResponse()
                    .should(hasStatusCode(SC_CREATED));

        userService
                .makeRequest()
                    .getUserLogins()
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasRegisteredLogin(expectedLoginInList));
    }

}
