package com.github.leonidstein.services;

import com.github.leonidstein.asserts.AssertableResponse;
import com.github.leonidstein.models.request.password.PasswordModel;
import com.github.leonidstein.models.request.token.JWTAuthModel;
import com.github.leonidstein.models.request.user.create.NewUserModel;
import com.github.leonidstein.utils.annotations.DELETE;
import com.github.leonidstein.utils.annotations.GET;
import com.github.leonidstein.utils.annotations.POST;
import com.github.leonidstein.utils.annotations.PUT;
import io.qameta.allure.Step;
import lombok.SneakyThrows;

import static com.github.leonidstein.constants.endpoint.JwtAuthEndpoint.CREATE_AUTH_TOKEN;
import static com.github.leonidstein.constants.endpoint.UserEndpoint.DELETE_USER;
import static com.github.leonidstein.constants.endpoint.UserEndpoint.GET_USER_INFO;
import static com.github.leonidstein.constants.endpoint.UserEndpoint.REGISTER_NEW_USER;
import static com.github.leonidstein.constants.endpoint.UserEndpoint.SHOW_LAST_LOGIN_100_USERS;
import static com.github.leonidstein.constants.endpoint.UserEndpoint.UPDATE_USER_PASSWORD;
import static java.lang.Thread.sleep;
import static java.time.Duration.ofMillis;

public final class UserService extends BaseService implements Requestable<UserService> {

    @Override
    public UserService makeRequest() {

        return this;
    }

    @POST
    @Step("Регистрация нового пользователя")
    public AssertableResponse registration(final NewUserModel user) {

        return makePostRequest(REGISTER_NEW_USER.getEndpoint(), user);
    }

    @POST
    @Step("Авторизация под пользователем")
    public AssertableResponse authorization(final NewUserModel user) {

        final JWTAuthModel jwtToken = new JWTAuthModel(user.getPass(), user.getLogin());

        return makePostRequest(CREATE_AUTH_TOKEN.getEndpoint(), jwtToken);
    }

    @POST
    @SneakyThrows
    @Step("Авторизация под пользователем с ожиданием")
    public AssertableResponse authorizationWithWaiting(final NewUserModel user) {

        sleep(ofMillis(1_500L));
        final JWTAuthModel jwtToken = new JWTAuthModel(user.getPass(), user.getLogin());

        return makePostRequest(CREATE_AUTH_TOKEN.getEndpoint(), jwtToken);
    }

    @POST
    @Step("Регистрация нового пользователя и авторизация")
    public AssertableResponse registrationWithAuthorization(final NewUserModel user) {

        registration(user);

        return authorization(user);
    }

    @GET
    @Step("Получение информации о пользователе")
    public AssertableResponse getInfoAboutUser(final String token) {

        return makeGetRequest(GET_USER_INFO.getEndpoint(), token);
    }

    @GET
    @Step("Получение информации о пользователе без токена авторизации")
    public AssertableResponse getInfoAboutUserWithoutToken() {

        return makeGetRequest(GET_USER_INFO.getEndpoint());
    }

    @GET
    @Step("Получение логинов последних 100 зарегистрированных пользователей")
    public AssertableResponse getUserLogins() {

        return makeGetRequest(SHOW_LAST_LOGIN_100_USERS.getEndpoint());
    }

    @DELETE
    @Step("Удаление пользователя")
    public AssertableResponse deleteUser(final String token) {

        return makeDeleteRequest(DELETE_USER.getEndpoint(), token);
    }

    @DELETE
    @Step("Удаление пользователя без токена авторизации")
    public AssertableResponse deleteUserWithoutToken() {

        return makeDeleteRequest(DELETE_USER.getEndpoint());
    }

    @PUT
    @Step("Обновление пароля пользователя")
    public AssertableResponse updateUserPassword(final String token, final String newPassword) {

        final PasswordModel newUserPassword = new PasswordModel(newPassword);

        return makePutRequest(UPDATE_USER_PASSWORD.getEndpoint(), token, newUserPassword);
    }

    @PUT
    @Step("Обновление пароля пользователя без токена авторизации")
    public AssertableResponse updateUserPasswordWithoutToken(final String newPassword) {

        final PasswordModel newUserPassword = new PasswordModel(newPassword);

        return makePutRequest(UPDATE_USER_PASSWORD.getEndpoint(), newUserPassword);
    }
}
