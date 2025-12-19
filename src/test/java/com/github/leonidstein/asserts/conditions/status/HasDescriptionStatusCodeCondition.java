package com.github.leonidstein.asserts.conditions.status;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.response.status.StatusDescriptionModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasDescriptionStatusCodeCondition(String expectedDescription) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String actualDescription = extractClass(response, StatusDescriptionModel.class).getDescription();

        assertThat(actualDescription).as("Проверка описания")
                                     .isNotNull()
                                     .isNotBlank()
                                     .isEqualTo(expectedDescription);
    }
}
