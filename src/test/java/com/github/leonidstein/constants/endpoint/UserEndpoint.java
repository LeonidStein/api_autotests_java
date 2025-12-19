package com.github.leonidstein.constants.endpoint;

import com.github.leonidstein.utils.annotations.DELETE;
import com.github.leonidstein.utils.annotations.GET;
import com.github.leonidstein.utils.annotations.POST;
import com.github.leonidstein.utils.annotations.PUT;
import lombok.Getter;

@Getter
public enum UserEndpoint {

    @GET
    GET_USER_INFO("/user"),

    @GET
    SHOW_LAST_LOGIN_100_USERS("/users"),

    @POST
    REGISTER_NEW_USER("/signup"),

    @PUT
    UPDATE_USER_PASSWORD("/user"),

    @DELETE
    DELETE_USER("/user");

    private final String endpoint;

    UserEndpoint(String endpoint) {

        this.endpoint = endpoint;
    }
}
