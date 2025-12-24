package com.github.leonidstein.asserts.conditions.common;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.response.info.InfoModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasStatusCondition(String expectedStatus) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String actualStatus = extractClass(response, InfoModel.class).getInfo().getStatus();

        assertThat(actualStatus).as("Проверка информационного статуса")
                                .isNotNull()
                                .isNotBlank()
                                .isEqualTo(expectedStatus);
    }
}
