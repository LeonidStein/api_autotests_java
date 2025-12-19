package com.github.leonidstein.constants.endpoint;

import com.github.leonidstein.utils.annotations.POST;
import lombok.Getter;

@Getter
public enum JwtAuthEndpoint {

    @POST
    CREATE_AUTH_TOKEN("/login");

    private final String endpoint;

    JwtAuthEndpoint(String endpoint) {

        this.endpoint = endpoint;
    }
}
