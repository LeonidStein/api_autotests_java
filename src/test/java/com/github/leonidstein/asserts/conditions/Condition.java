package com.github.leonidstein.asserts.conditions;

import io.restassured.response.ValidatableResponse;

@FunctionalInterface
public interface Condition {

    void check(ValidatableResponse response);
}
