package com.github.leonidstein.constants.endpoint;

import com.github.leonidstein.utils.annotations.GET;
import lombok.Getter;

@Getter
public enum ResponseTrainEndpoint {

    @GET
    GET_CARS("/easy/carBrands"),

    @GET
    GET_VARIOUS_KEY("/easy/nums"),

    @GET
    REDIRECTION("/easy/redirect"),

    @GET
    GET_CURRENT_VERSION("/easy/version");

    private final String endpoint;

    ResponseTrainEndpoint(String endpoint) {

        this.endpoint = endpoint;
    }
}
