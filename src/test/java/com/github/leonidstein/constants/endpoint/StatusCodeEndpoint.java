package com.github.leonidstein.constants.endpoint;

import com.github.leonidstein.utils.annotations.GET;
import lombok.Getter;

@Getter
public enum StatusCodeEndpoint {

    @GET
    GET_BAD_REQUEST("/bad-request"),

    @GET
    GET_CREATED("/created"),

    @GET
    GET_FORBIDDEN("/forbidden"),

    @GET
    GET_INVALID_URL("/invalid-url"),

    @GET
    GET_MOVED("/moved"),

    @GET
    GET_NO_CONTENT("/no-content"),

    @GET
    GET_UNAUTHORIZED("/unauthorized");

    private final String endpoint;

    StatusCodeEndpoint(String endpoint) {

        this.endpoint = endpoint;
    }
}
