package com.github.leonidstein.asserts.conditions.common;

import com.github.leonidstein.asserts.conditions.Condition;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractStatusCode;
import static org.assertj.core.api.Assertions.assertThat;

public record HasStatusCodeCondition(int expectedStatusCode) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        int actualStatusCode = extractStatusCode(response);

        assertThat(actualStatusCode).as("Проверка статус кода")
                                    .isEqualTo(expectedStatusCode);
    }
}
