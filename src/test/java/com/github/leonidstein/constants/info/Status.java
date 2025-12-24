package com.github.leonidstein.constants.info;

import lombok.Getter;

@Getter
public enum Status {

    SUCCESS("success"),

    FAIL("fail");

    private final String status;

    Status(String status) {

        this.status = status;
    }
}
