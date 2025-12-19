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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.github.leonidstein.asserts.conditions.ConditionManager.hasMessage;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatus;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatusCode;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasToken;
import static com.github.leonidstein.constants.info.Message.USER_SUCCESSFULLY_DELETED;
import static com.github.leonidstein.constants.info.Status.SUCCESS;
import static com.github.leonidstein.resolvers.UserType.NEW_USER;
import static com.github.leonidstein.resolvers.UserVariantType.WITHOUT_GAME;
import static com.github.leonidstein.utils.annotations.TestType.Type.NEGATIVE;
import static com.github.leonidstein.utils.annotations.TestType.Type.POSITIVE;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@Epic("API. user-controller-new")
@Feature("API. Удаление пользователя")
@ExtendWith(UserResolver.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public final class DeleteUserTest extends BaseTest {

    private String token;
    private NewUserModel user;

    @BeforeEach
    void initUserCredentials(@UserVariant(user = NEW_USER, variant = WITHOUT_GAME) final NewUserModel user) {

        this.token = userService.makeRequest()
                                    .registrationWithAuthorization(user)
                                .checkResponse()
                                    .should(hasStatusCode(SC_OK))
                                    .should(hasToken())
                                .extractToken();
        this.user = user;
    }

    @DisplayName("Удаление пользователя")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/31")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(CRITICAL)
    @TestType(POSITIVE)
    @Test
    public void testDeleteUser() {

        userService
                .makeRequest()
                    .deleteUser(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(USER_SUCCESSFULLY_DELETED.getMessage()));

        userService
                .makeRequest()
                    .getInfoAboutUser(token)
                .checkResponse()
                    .should(hasStatusCode(SC_UNAUTHORIZED));
    }

    @DisplayName("Повторное удаление пользователя")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/32")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testRepeatedDeleteUser() {

        userService
                .makeRequest()
                    .deleteUser(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(USER_SUCCESSFULLY_DELETED.getMessage()));

        userService
                .makeRequest()
                    .deleteUser(token)
                .checkResponse()
                    .should(hasStatusCode(SC_UNAUTHORIZED));
    }

    @DisplayName("Авторизация пользователя после его удаления")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/33")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(CRITICAL)
    @TestType(NEGATIVE)
    @Test
    public void testAuthorizationDeletedUser() {

        userService
                .makeRequest()
                    .deleteUser(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(USER_SUCCESSFULLY_DELETED.getMessage()));

        userService
                .makeRequest()
                    .authorization(user)
                .checkResponse()
                    .should(hasStatusCode(SC_UNAUTHORIZED));
    }

    @DisplayName("Удаление пользователя без токена авторизации")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/56")
    @Tags({@Tag("api"), @Tag("api-smoke")})
    @Severity(NORMAL)
    @TestType(POSITIVE)
    @Test
    public void testDeleteUserWithoutToken() {

        userService
                .makeRequest()
                    .deleteUserWithoutToken()
                .checkResponse()
                    .should(hasStatusCode(SC_UNAUTHORIZED));
    }

}
