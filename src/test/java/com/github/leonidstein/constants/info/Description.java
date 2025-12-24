package com.github.leonidstein.constants.info;

import lombok.Getter;

@Getter
public enum Description {

    BAD_REQUEST("Bad Request"),

    CREATED("created"),

    FORBIDDEN("Forbidden"),

    NOT_FOUND("Not Found"),

    MOVED_PERMANENTLY("Moved Permanently");

    final String description;

    Description(String description) {

        this.description = description;
    }
}
