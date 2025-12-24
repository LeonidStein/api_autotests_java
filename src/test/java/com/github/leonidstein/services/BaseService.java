package com.github.leonidstein.services;

import com.github.leonidstein.asserts.AssertableResponse;
import com.github.leonidstein.utils.ReportTpl;
import com.github.leonidstein.utils.annotations.DELETE;
import com.github.leonidstein.utils.annotations.GET;
import com.github.leonidstein.utils.annotations.POST;
import com.github.leonidstein.utils.annotations.PUT;
import io.qameta.allure.Step;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.MULTIPART;

public abstract class BaseService {

    private RequestSpecification make() {

        return given().filter(new RequestLoggingFilter(LogDetail.ALL))
                      .filter(new ResponseLoggingFilter(LogDetail.ALL))
                      .filter(ReportTpl.getInstance().withTemplates());
    }

    @POST
    @Step("POST запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makePostRequest(final String endpoint, final Object body) {

        return new AssertableResponse(
                make()
                    .contentType(JSON)
                    .body(body)
                .when()
                    .post(endpoint)
                .then()
        );
    }

    @POST
    @Step("POST запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makePostRequest(final String endpoint, final String token) {

        return new AssertableResponse(
                make()
                    .contentType(JSON)
                    .auth().oauth2(token)
                .when()
                    .post(endpoint)
                .then()
        );
    }

    @POST
    @Step("POST запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makePostRequest(final String endpoint, final String token, final Object body) {

        return new AssertableResponse(
                make()
                    .contentType(JSON)
                    .auth().oauth2(token)
                    .body(body)
                .when()
                    .post(endpoint)
                .then()
        );
    }

    @POST
    @Step("POST запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makePostRequest(final String endpoint, final String fieldName, final File body) {

        return new AssertableResponse(
                make()
                    .contentType(MULTIPART)
                    .multiPart(fieldName, body)
                .when()
                    .post(endpoint)
                .then()
        );
    }

    @GET
    @Step("GET запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makeGetRequest(final String endpoint, final String token) {

        return new AssertableResponse(
                make()
                    .auth().oauth2(token)
                .when()
                    .get(endpoint)
                .then()
        );
    }

    @GET
    @Step("GET запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makeGetRequest(final String endpoint) {

        return new AssertableResponse(
                make()
                .when()
                    .get(endpoint)
                .then()
        );
    }

    @GET
    @Step("GET запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makeGetRequest(final String endpoint, final boolean isRedirected) {

        return new AssertableResponse(
                make()
                    .redirects().follow(isRedirected)
                .when()
                    .get(endpoint)
                .then()
        );
    }

    @GET
    @Step("GET запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makeGetRequest(final String endpoint, final String path,
            final String token, final String pathParameter) {

        return new AssertableResponse(
                make()
                    .pathParam(path, pathParameter)
                    .auth().oauth2(token)
                .when()
                    .get(endpoint)
                .then()
        );
    }

    @GET
    @Step("GET запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makeGetRequest(final String endpoint, final String path, final String pathParameter) {

        return new AssertableResponse(
                make()
                    .pathParam(path, pathParameter)
                .when()
                    .get(endpoint)
                .then()
        );
    }

    @DELETE
    @Step("DELETE запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makeDeleteRequest(final String endpoint, final String token) {

        return new AssertableResponse(
                make()
                    .auth().oauth2(token)
                .when()
                    .delete(endpoint)
                .then()
        );
    }

    @DELETE
    @Step("DELETE запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makeDeleteRequest(final String endpoint) {

        return new AssertableResponse(
                make()
                .when()
                    .delete(endpoint)
                .then()
        );
    }

    @DELETE
    @Step("DELETE запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makeDeleteRequest(final String endpoint, final String path,
            final String token, final String pathParameter) {

        return new AssertableResponse(
                make()
                    .pathParam(path, pathParameter)
                    .auth().oauth2(token)
                .when()
                    .delete(endpoint)
                .then()
        );
    }

    @DELETE
    @Step("DELETE запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makeDeleteRequest(final String endpoint, final String path, final String pathParameter) {

        return new AssertableResponse(
                make()
                    .pathParam(path, pathParameter)
                .when()
                    .delete(endpoint)
                .then()
        );
    }

    @DELETE
    @Step("DELETE запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makeDeleteRequest(final String endpoint, final String path,
            final String token, final String pathParameter, final List<?> bodyList) {

        return new AssertableResponse(
                make()
                    .contentType(JSON)
                    .pathParam(path, pathParameter)
                    .auth().oauth2(token)
                    .body(bodyList)
                .when()
                    .delete(endpoint)
                .then()
        );
    }

    @DELETE
    @Step("DELETE запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makeDeleteRequest(final String endpoint, final String path,
            final String pathParameter, final List<?> bodyList) {

        return new AssertableResponse(
                make()
                    .contentType(JSON)
                    .pathParam(path, pathParameter)
                    .body(bodyList)
                .when()
                    .delete(endpoint)
                .then()
        );
    }

    @PUT
    @Step("PUT запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makePutRequest(final String endpoint, final String token, final Object body) {

        return new AssertableResponse(
                make()
                    .contentType(JSON)
                    .auth().oauth2(token)
                    .body(body)
                .when()
                    .put(endpoint)
                .then()
        );
    }

    @PUT
    @Step("PUT запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makePutRequest(final String endpoint, final String path,
            final String token, final String pathParameter, final Object body) {

        return new AssertableResponse(
                make()
                    .contentType(JSON)
                    .pathParam(path, pathParameter)
                    .auth().oauth2(token)
                    .body(body)
                .when()
                    .put(endpoint)
                .then()
        );
    }

    @PUT
    @Step("PUT запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makePutRequest(final String endpoint, final Object body) {

        return new AssertableResponse(
                make()
                    .contentType(JSON)
                    .body(body)
                .when()
                    .put(endpoint)
                .then()
        );
    }

    @PUT
    @Step("PUT запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makePutRequest(final String endpoint, final String path,
            final String token, final String pathParameter, final List<?> bodyList) {

        return new AssertableResponse(
                make()
                    .contentType(JSON)
                    .pathParam(path, pathParameter)
                    .auth().oauth2(token)
                    .body(bodyList)
                .when()
                    .put(endpoint)
                .then()
        );
    }

    @PUT
    @Step("PUT запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makePutRequest(final String endpoint, final String path, final String token,
            final String pathParameter) {

        return new AssertableResponse(
                make()
                    .contentType(JSON)
                    .pathParam(path, pathParameter)
                    .auth().oauth2(token)
                .when()
                    .put(endpoint)
                .then()
        );
    }

    @PUT
    @Step("PUT запрос, эндпоинт: {endpoint}")
    protected AssertableResponse makePutRequest(final String endpoint, final String path, final String pathParameter,
            final List<?> bodyList) {

        return new AssertableResponse(
                make()
                    .contentType(JSON)
                    .pathParam(path, pathParameter)
                    .body(bodyList)
                .when()
                    .put(endpoint)
                .then()
        );
    }
}
