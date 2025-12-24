package com.github.leonidstein.constants.endpoint;

import com.github.leonidstein.utils.annotations.DELETE;
import com.github.leonidstein.utils.annotations.GET;
import com.github.leonidstein.utils.annotations.POST;
import com.github.leonidstein.utils.annotations.PUT;
import lombok.Getter;

@Getter
public enum GameEndpoint {

    @GET
    GET_GAMES("/user/games"),

    @GET
    GET_GAME("/user/games/{id}"),

    @PUT
    UPDATE_GAME_DLC_INFO("/user/games/{gameId}"),

    @PUT
    UPDATE_GAME_FIELD("/user/games/{gameId}/updateField"),

    @DELETE
    DELETE_GAME("/user/games/{id}"),

    @DELETE
    DELETE_DLC("/user/games/{id}/dlc"),

    @POST
    ADD_GAMES("/user/games");

    private final String endpoint;

    GameEndpoint(String endpoint) {

        this.endpoint = endpoint;
    }
}
