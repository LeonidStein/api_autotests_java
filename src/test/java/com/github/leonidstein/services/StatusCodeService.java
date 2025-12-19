package com.github.leonidstein.services;

import com.github.leonidstein.asserts.AssertableResponse;
import com.github.leonidstein.utils.annotations.GET;
import io.qameta.allure.Step;

import static com.github.leonidstein.constants.endpoint.StatusCodeEndpoint.GET_BAD_REQUEST;
import static com.github.leonidstein.constants.endpoint.StatusCodeEndpoint.GET_CREATED;
import static com.github.leonidstein.constants.endpoint.StatusCodeEndpoint.GET_FORBIDDEN;
import static com.github.leonidstein.constants.endpoint.StatusCodeEndpoint.GET_INVALID_URL;
import static com.github.leonidstein.constants.endpoint.StatusCodeEndpoint.GET_MOVED;
import static com.github.leonidstein.constants.endpoint.StatusCodeEndpoint.GET_NO_CONTENT;
import static com.github.leonidstein.constants.endpoint.StatusCodeEndpoint.GET_UNAUTHORIZED;

public final class StatusCodeService extends BaseService implements Requestable<StatusCodeService> {

    @Override
    public StatusCodeService makeRequest() {

        return this;
    }

    @GET
    @Step("Получение статус кода 400 Bad Request")
    public AssertableResponse getBadRequest() {

        return makeGetRequest(GET_BAD_REQUEST.getEndpoint());
    }

    @GET
    @Step("Получение статус кода 201 Created")
    public AssertableResponse getCreated() {

        return makeGetRequest(GET_CREATED.getEndpoint());
    }

    @GET
    @Step("Получение статус кода 403 Forbidden")
    public AssertableResponse getForbidden() {

        return makeGetRequest(GET_FORBIDDEN.getEndpoint());
    }

    @GET
    @Step("Получение статус кода 404 Not Found")
    public AssertableResponse getNotFound() {

        return makeGetRequest(GET_INVALID_URL.getEndpoint());
    }

    @GET
    @Step("Получение статус кода 301 Moved Permanently")
    public AssertableResponse getMovedPermanently() {

        final boolean isRedirected = false;

        return makeGetRequest(GET_MOVED.getEndpoint(), isRedirected);
    }

    @GET
    @Step("Получение статус кода 204 No Content")
    public AssertableResponse getNoContent() {

        return makeGetRequest(GET_NO_CONTENT.getEndpoint());
    }

    @GET
    @Step("Получение статус кода 401 Unauthorized")
    public AssertableResponse getUnauthorized() {

        return makeGetRequest(GET_UNAUTHORIZED.getEndpoint());
    }
}
