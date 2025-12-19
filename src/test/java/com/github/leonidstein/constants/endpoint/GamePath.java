package com.github.leonidstein.constants.endpoint;

import lombok.Getter;

@Getter
public enum GamePath {

    GAME_ID("gameId"),

    ID("id");

    private final String path;

    GamePath(final String path) {

        this.path = path;
    }
}
